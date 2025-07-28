package com.ifs_jogos.demo.services.esporte;

import com.ifs_jogos.demo.models.*;
import com.ifs_jogos.demo.repositories.EsporteRepository;
import com.ifs_jogos.demo.repositories.EventoRepository;
import com.ifs_jogos.demo.repositories.JogoRepository;
import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import com.ifs_jogos.demo.services.esporte.dto.EsporteDTO;
import com.ifs_jogos.demo.services.esporte.form.EsporteForm;
import com.ifs_jogos.demo.services.jogo.JogoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EsporteService {

    private final EsporteRepository esporteRepository;
    private final EventoRepository eventoRepository;
    private final JogoRepository jogoRepository;
    private final JogoService jogoService;

    @Transactional
    public EsporteDTO createEsporte(EsporteForm form) {
        Evento evento = eventoRepository.findById(form.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        Esporte esporte = esporteRepository.save(form.paraModel(evento));

        return EsporteDTO.deModel(esporte);
    }


    public List<EsporteDTO> getEsportes() {
        List<Esporte> esporteList = esporteRepository.findAll();
        List<EsporteDTO> dtoList = new ArrayList<>();

        for (Esporte e : esporteList) {
            dtoList.add(EsporteDTO.deModel(e));
        }

        return dtoList;
    }


    public EsporteDTO findByNome(String nome) {
        Esporte esporte = esporteRepository.findByNome(nome);

        return EsporteDTO.deModel(esporte);
    }

    @Transactional
    public EquipeDTO getCampeao(Integer esporteId) {
        Esporte esporte = esporteRepository.findById(esporteId)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        if (!jogoRepository.findByStatusAndGrupo_Esporte(JogoStatusEnum.PENDENTE, esporte).isEmpty()) {
            throw new RuntimeException("Ainda há jogos.");
        }

        if(esporte.getGrupos().size()>1) {
            Jogo jogoFinal = getJogoFinal(esporte);

            if(jogoFinal.getPlacarEquipeA() > jogoFinal.getPlacarEquipeB()) {
                esporte.setCampeao(jogoFinal.getEquipeA());
            } else {
                esporte.setCampeao(jogoFinal.getEquipeB());
            }
        } else {
            List<Grupo> grupos = esporte.getGrupos();
            List<Equipe> equipes = grupos.getFirst().getEquipes();

            equipes.sort((e1, e2) -> e2.getPontos().compareTo(e1.getPontos()));

            esporte.setCampeao(equipes.getFirst());
        }

        esporteRepository.save(esporte);
        return EquipeDTO.deModel(esporte.getCampeao());
    }

    private Jogo getJogoFinal(Esporte esporte) {
        List<Jogo> jogos = jogoRepository.findByFaseAndEquipeA_Esporte(FaseEnum.FINAL, esporte);

        if (jogos.isEmpty()) {
            throw new RuntimeException("As finais desse esporte ainda não aconteceram");
        }

        return jogos.getFirst();
    }

    @Transactional
    public EsporteDTO updateEsporte(Integer id, EsporteForm form) {
        Esporte existente = esporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        if (form.getNome()!=null) existente.setNome(form.getNome());
        if (form.getMinAtletas()!=null) existente.setMinAtletas(form.getMinAtletas());
        if (form.getMaxAtletas()!=null) existente.setMaxAtletas(form.getMaxAtletas());

        return EsporteDTO.deModel(esporteRepository.save(existente));
    }

    @Transactional
    public EsporteDTO deleteEsporte(Integer id) {
        Esporte esporte = esporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        esporteRepository.delete(esporte);

        return EsporteDTO.deModel(esporte);
    }
}
