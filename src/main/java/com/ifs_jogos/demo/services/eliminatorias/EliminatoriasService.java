package com.ifs_jogos.demo.services.eliminatorias;

import com.ifs_jogos.demo.models.*;

import com.ifs_jogos.demo.repositories.*;
import com.ifs_jogos.demo.services.eliminatorias.dto.EliminatoriasDTO;
import com.ifs_jogos.demo.services.eliminatorias.form.FaseForm;
import com.ifs_jogos.demo.services.jogo.JogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EliminatoriasService {

    private final JogoRepository jogoRepository;
    private final JogoService jogoService;
    private final GrupoRepository grupoRepository;
    private final EsporteRepository esporteRepository;
    private final EquipeRepository equipeRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void gerarEliminatorias(String esporteNome) {
        Esporte esporte = esporteRepository.findByNome(esporteNome);
        if(esporte==null) {
            throw new RuntimeException("Esporte nao encontrado.");
        }

        if (jogoRepository.existsEliminatoria(esporte)) {
            throw new RuntimeException("Esse esporte já tem jogos eliminatorios gerados.");
        }

        if (existeJogoDeGrupoPendente(esporte)) {
            throw new RuntimeException("Ainda há jogos na fase de grupos que não foram finalizados");
        }

        List<Equipe> classificados = getClassificados(esporte);

        if(classificados.size() == 4) {
            gerarConfrontos(classificados, FaseEnum.SEMIFINAL);
        } else if (classificados.size() == 6  || classificados.size() == 8) {
            gerarConfrontos(gerarEquipesBye(classificados, 8), FaseEnum.QUARTASFINAL);
        } else if (classificados.size() > 8) {
            gerarConfrontos(gerarEquipesBye(classificados, 16), FaseEnum.OITAVASFINAL);
        }
    }

    @Transactional
    private void gerarConfrontos(List<Equipe> equipes, FaseEnum fase) {
        Esporte esporte = equipes.getFirst().getEsporte();
        if (esporte == null) {
            throw new RuntimeException("Esporte não encontrado na equipe.");
        }

        LocalDateTime dataInicial = LocalDateTime.of(esporte.getEvento().getDataInicio(), LocalTime.of(13, 0));

        Jogo ultimoJogo = jogoRepository.buscarUltimoJogoPorEsporte(esporte.getId());
        if(ultimoJogo!=null) {
            dataInicial = ultimoJogo.getDataHora().plusDays(1).withHour(13).withMinute(0);
        }

        List<Usuario> listArbitros = usuarioRepository.findByTipoUsuario(UsuarioEnum.ARBITRO);
        if (listArbitros == null || listArbitros.isEmpty()) {
            throw new RuntimeException("Árbitros insuficientes.");
        }

        Random random = new Random();
        Duration intervalo = Duration.ofHours(1);
        int contador = 0;

        for (int i = 0; i < equipes.size(); i += 2) {
            Equipe equipeA = equipes.get(i);
            Equipe equipeB = equipes.get(i + 1);

            int placarA = 0;
            int placarB = 0;

            boolean isBye = isEquipeBye(equipeA) || isEquipeBye(equipeB);
            if(isBye && isEquipeBye(equipeA)) {
                placarB = 1;
            }
            if(isBye && isEquipeBye(equipeB)) {
                placarA = 1;
            }
            LocalDateTime dataHora = dataInicial.plus(intervalo.multipliedBy(contador));
            Jogo jogo = Jogo.builder()
                    .equipeA(equipeA)
                    .equipeB(equipeB)
                    .fase(fase)
                    .dataHora(dataHora)
                    .status(isBye ? JogoStatusEnum.WO : JogoStatusEnum.PENDENTE)
                    .placarEquipeA(placarA)
                    .placarEquipeB(placarB)
                    .arbitro(listArbitros.get(random.nextInt(listArbitros.size())))
                    .build();

            jogoRepository.save(jogo);
            contador++;
        }
    }

    public void gerarProximaFase(FaseForm form) {
        Esporte esporte = esporteRepository.findByNome(form.getNomeEsporte());
        if(esporte==null) {
            throw new RuntimeException("Esporte não encontrado");
        }
        if(existeJogoEliminatorioPendente(form)) {
            throw new RuntimeException("A fase atual ainda não foi finalizada!");
        }
        FaseEnum faseEnum = FaseEnum.valueOf(form.getFaseAtual());
        List<Jogo> jogos = jogoRepository.findByFaseAndEquipeA_Esporte(faseEnum, esporte);
        List<Equipe> vencedores = new ArrayList<>();

        for (Jogo jogo : jogos) {
            if (jogo.getPlacarEquipeA() > jogo.getPlacarEquipeB()) {
                vencedores.add(jogo.getEquipeA());
            } else {
                vencedores.add(jogo.getEquipeB());
            }
        }

        FaseEnum proximaFase = getFaseEnum(form);

        if(!jogoRepository.findByFaseAndEquipeA_Esporte(proximaFase, esporte).isEmpty()) {
            throw new RuntimeException("Essa fase já foi gerada");
        }

        gerarConfrontos(vencedores, proximaFase);
    }

    private FaseEnum getFaseEnum(FaseForm form) {
        FaseEnum proximaFase = null;
        if (form.getFaseAtual().equals("OITAVASFINAL")) {
            proximaFase = FaseEnum.QUARTASFINAL;
        } else if(form.getFaseAtual().equals("QUARTASFINAL")) {
            proximaFase = FaseEnum.SEMIFINAL;
        } else if (form.getFaseAtual().equals("SEMIFINAL")) {
            proximaFase = FaseEnum.FINAL;
        } else if(form.getFaseAtual().equals("FINAL")) {
            proximaFase = null;
        } else {
            throw new RuntimeException("Fase inválida!");
        }
        return proximaFase;
    }

    private List<Equipe> getClassificados(Esporte esporte) {
        List<Grupo> grupos = grupoRepository.findByEsporte(esporte);

        List<Equipe> primeiros = new ArrayList<>();
        List<Equipe> segundos = new ArrayList<>();

        for(Grupo grupo : grupos) {
            List<Equipe> equipes = grupo.getEquipes();

            equipes.sort((e1, e2) -> e2.getPontos().compareTo(e1.getPontos()));

            if (equipes.size() >= 2) {
                primeiros.add(equipes.get(0));
                segundos.add(equipes.get(1));
            }
        }

        List<Equipe> classificados = new ArrayList<>();

        int n = primeiros.size();
        for (int i = 0; i < n; i++) {
            classificados.add(primeiros.get(i));
            classificados.add(segundos.get(n - 1 - i));
        }

        return classificados;
    }

    @Transactional
    private List<Equipe> gerarEquipesBye(List<Equipe> equipes, Integer tamanhoIdeal) {
        int total = equipes.size();
        int byesNecessarios = tamanhoIdeal - total;
        if (byesNecessarios == 0) {
            return equipes;
        }
        List<Equipe> resultado = new ArrayList<>();
        int intervalo = total / byesNecessarios;
        int insertCount = 0;

        for (int i = 0; i < equipes.size(); i++) {
            resultado.add(equipes.get(i));
            if ((i + 1) % intervalo == 0 && insertCount < byesNecessarios) {
                Equipe bye = Equipe.builder()
                        .nome("BYE " + (insertCount++))
                        .campus(equipes.getFirst().getCampus())
                        .atletas(null)
                        .curso(equipes.getFirst().getCurso())
                        .esporte(equipes.getFirst().getEsporte())
                        .build();
                resultado.add(bye);
                equipeRepository.save(bye);
            }
        }
        return resultado;
    }

    private boolean existeJogoDeGrupoPendente(Esporte esporte) {
        List<Jogo> jogos = jogoRepository.findByStatusAndGrupo_Esporte(JogoStatusEnum.PENDENTE, esporte);

        return !jogos.isEmpty();
    }

    private boolean existeJogoEliminatorioPendente(FaseForm form) {
        Esporte esporte = esporteRepository.findByNome(form.getNomeEsporte());
        if(esporte==null) {
            throw new RuntimeException("Esporte não encontrado");
        }
        FaseEnum faseEnum = FaseEnum.valueOf(form.getFaseAtual());
        return jogoRepository
                .existeJogoEliminatorioPendente(faseEnum, JogoStatusEnum.PENDENTE, esporte);
    }

    private boolean isEquipeBye(Equipe equipe) {
        return equipe.getNome() != null && equipe.getNome().startsWith("BYE");
    }

    public EliminatoriasDTO getEliminatorias(FaseForm form) {
        EliminatoriasDTO eliminatorias = new EliminatoriasDTO();
        FaseEnum faseEnum = FaseEnum.valueOf(form.getFaseAtual());
        eliminatorias.setJogos(jogoService.findByFaseAndEsporte(form.getFaseAtual(), form.getNomeEsporte()));

        return eliminatorias;
    }

}