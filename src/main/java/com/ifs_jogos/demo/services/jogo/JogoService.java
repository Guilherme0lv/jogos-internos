package com.ifs_jogos.demo.services.jogo;

import com.ifs_jogos.demo.models.*;
import com.ifs_jogos.demo.repositories.*;

import com.ifs_jogos.demo.services.jogo.dto.JogoDTO;
import com.ifs_jogos.demo.services.jogo.form.PlacarForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class JogoService {

    private final JogoRepository jogoRepository;
    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EquipeRepository equipeRepository;
    private final EsporteRepository esporteRepository;

    @Transactional
    public List<JogoDTO> gerarJogos(Integer grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado."));

        List<Equipe> equipes = grupo.getEquipes();

        Evento evento = grupo.getEsporte().getEvento();

        LocalDate dataInicio = evento.getDataInicio();
        LocalDate dataFim = evento.getDataFim();
        if (dataInicio.isAfter(dataFim)) {
            throw new RuntimeException("Período do evento é inválido.");
        }

        LocalDateTime horarioInicial = LocalDateTime.of(dataInicio, LocalTime.of(13, 0));
        //Evita que os jogos sejam no mesmo horario dos outros grupos.
        Jogo ultimoJogo = jogoRepository.buscarUltimoJogoPorGrupo(grupo.getEsporte().getId());
        if (ultimoJogo!=null) {
            horarioInicial = (ultimoJogo.getDataHora().plusDays(1).withHour(13).withMinute(0));
        }

        Duration intervaloEntreJogos = Duration.ofHours(1);

        List<Usuario> listArbitros = usuarioRepository.findByTipoUsuario(UsuarioEnum.ARBITRO);
        if (listArbitros == null || listArbitros.isEmpty()) {
            throw new RuntimeException("Arbitros insuficientes.");
        }

        List<Jogo> jogos = new ArrayList<>();
        int contador = 0;

        for (int i = 0; i < equipes.size(); i++) {
            for (int j = i + 1; j < equipes.size(); j++) {
                LocalDateTime dataHoraJogo = horarioInicial.plus(intervaloEntreJogos.multipliedBy(contador));
                if (dataHoraJogo.toLocalDate().isAfter(dataFim)) {
                    throw new RuntimeException("Número de jogos excede o período disponível do evento.");
                }
                Jogo jogo = new Jogo();
                jogo.setGrupo(grupo);
                jogo.setEquipeA(equipes.get(i));
                jogo.setEquipeB(equipes.get(j));
                jogo.setPlacarEquipeA(0);
                jogo.setPlacarEquipeB(0);
                jogo.setFase(FaseEnum.GRUPO);
                jogo.setArbitro(listArbitros.get(new Random().nextInt(listArbitros.size())));
                jogo.setDataHora(dataHoraJogo);

                jogos.add(jogo);
                contador++;
            }
        }
        return jogoRepository.saveAll(jogos).stream()
                .map(JogoDTO::deModel)
                .toList();
    }

    public List<JogoDTO> getJogos() {
        return jogoRepository.findAll()
                .stream()
                .map(JogoDTO::deModel)
                .toList();
    }


    public List<JogoDTO> findByFinalizado() {
        return jogoRepository.findByFinalizadoTrue()
                .stream()
                .map(JogoDTO::deModel)
                .toList();
    }


    public List<JogoDTO> findByGrupo(Integer grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado."));

        return jogoRepository.findByGrupo(grupo)
                .stream()
                .map(JogoDTO::deModel)
                .toList();
    }


    public List<JogoDTO> findByFaseAndEsporte(FaseEnum fase, Integer esporteId) {
        Esporte esporte = esporteRepository.findById(esporteId)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        List<Jogo> jogos = jogoRepository.findByFaseAndEquipeA_Esporte(fase, esporte);

        return jogos.stream().map(JogoDTO::deModel).toList();
    }

    @Transactional
    public JogoDTO finalizarJogo(PlacarForm form) {

        if (form.getPlacarA() < 0 || form.getPlacarB() < 0) {
            throw new RuntimeException("Placar não pode ser menor que 0!");
        }

        Jogo jogo = jogoRepository.findById(form.getIdJogo())
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado."));

        if (jogo.isFinalizado()) {
            throw new RuntimeException("Esse jogo já foi finalizado");
        }

        Equipe equipeA = jogo.getEquipeA();
        Equipe equipeB = jogo.getEquipeB();

        jogo.setPlacarEquipeA(form.getPlacarA());
        jogo.setPlacarEquipeB(form.getPlacarB());
        jogo.setFinalizado(true);

        if (jogo.getFase() == FaseEnum.GRUPO) {
            if (jogo.getPlacarEquipeA() > jogo.getPlacarEquipeB()) { //vitoria A
                equipeA.setPontos(equipeA.getPontos() + 3);
                equipeA.setVitorias(equipeA.getVitorias() + 1);
                equipeB.setDerrotas(equipeB.getDerrotas() + 1);
            } else if(jogo.getPlacarEquipeB() > jogo.getPlacarEquipeA()){ //vitoria B
                equipeB.setPontos(equipeB.getPontos() + 3);
                equipeB.setVitorias(equipeB.getVitorias() + 1);
                equipeA.setDerrotas(equipeA.getDerrotas() + 1);
            } else { // empate
                equipeA.setPontos(equipeA.getPontos() + 1);
                equipeB.setPontos(equipeB.getPontos() + 1);
            }
        }

        equipeRepository.save(equipeA);
        equipeRepository.save(equipeB);
        jogoRepository.save(jogo);

        return JogoDTO.deModel(jogo);
    }

    @Transactional
    public JogoDTO aplicarWO(Integer jogoId, Integer idEquipeVencedora) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado."));

        if (jogo.isFinalizado()) {
            throw new RuntimeException("Esse jogo já foi finalizado");
        }

        Equipe equipeA = jogo.getEquipeA();
        Equipe equipeB = jogo.getEquipeB();
        if (idEquipeVencedora.equals(jogo.getEquipeA().getId())) {
            jogo.setPlacarEquipeA(1);
            jogo.setPlacarEquipeB(0);
            if (jogo.getFase() == FaseEnum.GRUPO) {
                equipeA.setPontos(equipeA.getPontos() + 3);
                equipeA.setVitorias(equipeA.getVitorias() + 1);
                equipeB.setDerrotas(equipeB.getDerrotas() + 1);
            }

        } else if (idEquipeVencedora.equals(jogo.getEquipeB().getId())){
            jogo.setPlacarEquipeA(0);
            jogo.setPlacarEquipeB(1);
            if (jogo.getFase() == FaseEnum.GRUPO) {
                equipeB.setPontos(equipeB.getPontos() + 3);
                equipeB.setVitorias(equipeB.getVitorias() + 1);
                equipeA.setDerrotas(equipeA.getDerrotas() + 1);
            }
        } else {
            throw new RuntimeException("Equipe não encontrado.");
        }

        jogo.setFinalizado(true);

        equipeRepository.save(equipeA);
        equipeRepository.save(equipeB);
        jogoRepository.save(jogo);

        return JogoDTO.deModel(jogo);
    }

    @Transactional
    public JogoDTO desfazerWO(Integer jogoId) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado."));

        if (!jogo.isFinalizado()) {
            throw new RuntimeException("Esse jogo não foi finalizado");
        }

        Equipe equipeA = jogo.getEquipeA();
        Equipe equipeB = jogo.getEquipeB();

        if(jogo.getPlacarEquipeA() > jogo.getPlacarEquipeB()) {
            jogo.setPlacarEquipeA(0);
            if (jogo.getFase() == FaseEnum.GRUPO) {
                equipeA.setPontos(equipeA.getPontos() - 3);
                equipeA.setVitorias(equipeA.getVitorias() - 1);
                equipeB.setDerrotas(equipeB.getDerrotas() - 1);
            }
        } else {
            jogo.setPlacarEquipeB(0);
            if (jogo.getFase() == FaseEnum.GRUPO) {
                equipeB.setPontos(equipeB.getPontos() - 3);
                equipeB.setVitorias(equipeB.getVitorias() - 1);
                equipeA.setDerrotas(equipeA.getDerrotas() - 1);
            }
        }
        jogo.setFinalizado(false);
        equipeRepository.save(equipeA);
        equipeRepository.save(equipeB);
        jogoRepository.save(jogo);

        return JogoDTO.deModel(jogo);
    }

    @Transactional
    public JogoDTO deleteJogoById(Integer jogoId) {
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado."));
        jogoRepository.delete(jogo);

        return JogoDTO.deModel(jogo);
    }

    @Transactional
    public void deleteAllJogos() {
        jogoRepository.deleteAll();
    }

    @Transactional
    public void deleteAllJogosDeGrupo(Integer esporteId) {
        Esporte esporte = esporteRepository.findById(esporteId)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        List<Jogo> jogos = jogoRepository.findByFaseAndEquipeA_Esporte(FaseEnum.GRUPO, esporte);

        jogoRepository.deleteAll(jogos);
    }
}