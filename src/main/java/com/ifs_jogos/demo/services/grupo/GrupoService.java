package com.ifs_jogos.demo.services.grupo;

import com.ifs_jogos.demo.models.Equipe;
import com.ifs_jogos.demo.models.Esporte;
import com.ifs_jogos.demo.models.Grupo;
import com.ifs_jogos.demo.repositories.EquipeRepository;

import com.ifs_jogos.demo.repositories.EsporteRepository;
import com.ifs_jogos.demo.repositories.GrupoRepository;
import com.ifs_jogos.demo.services.grupo.dto.ClassificacaoDTO;
import com.ifs_jogos.demo.services.grupo.dto.GrupoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final EquipeRepository equipeRepository;
    private final EsporteRepository esporteRepository;

    @Transactional
    public void gerarGrupos(Integer esporteId) {
        Esporte esporte = esporteRepository.findById(esporteId)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        List<Equipe> equipes = equipeRepository.findByEsporte(esporte);
        int total = equipes.size();

        if (total < 3) {
            throw new RuntimeException("Não há equipes suficientes para formar grupos");
        }

        Collections.shuffle(equipes);
        List<Grupo> grupos = new ArrayList<>();

        if (total <= 5) {
            Grupo grupo = new Grupo();
        grupo.setNome(equipes.getFirst().getEsporte().getNome() + "-" + esporte.getEvento().getTipoEvento() + " Grupo " + 1);
            grupo.setEsporte(esporte);
            grupo = grupoRepository.save(grupo);
            grupos.add(grupo);

            for (Equipe equipe : equipes) {
                equipe.setGrupo(grupo);
                equipeRepository.save(equipe);
            }
            return;
        }

        int numGrupos = total / 3;
        int resto = total % 3;
        // Cria os grupos
        for (int i = 1; i <= numGrupos; i++) {
            Grupo grupo = new Grupo();
            grupo.setNome(equipes.getFirst().getEsporte().getNome() + "-" + esporte.getEvento().getTipoEvento() + " Grupo " + i);
            grupo.setEsporte(esporte);
            grupo = grupoRepository.save(grupo);
            grupos.add(grupo);
        }

        int equipeIndex = 0;
        // Atualiza as equipes com seus grupos
        for (Grupo grupo : grupos) {
            for (int i = 0; i < 3; i++) {
                Equipe equipe = equipes.get(equipeIndex++);
                equipe.setGrupo(grupo);
                equipeRepository.save(equipe);
            }
        }
        // Com base no resto, distribui as equipes restantes nos grupos
        if (resto > 0) {
            for (int i = 0; i < resto; i++) {
                Grupo grupo = grupos.get(i);
                Equipe equipe = equipes.get(equipeIndex++);
                equipe.setGrupo(grupo);
                equipeRepository.save(equipe);
            }
        }
    }

    public List<GrupoDTO> getGrupos() {
        List<Grupo> gruposList = grupoRepository.findAll();
        List<GrupoDTO> dtoList = new ArrayList<>();
        for (Grupo g : gruposList) {
            dtoList.add(GrupoDTO.deModel(g));
        }
        return dtoList;
    }

    public List<ClassificacaoDTO> getClassificacao(Integer grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado."));

        List<Equipe> equipes = grupo.getEquipes();

        equipes.sort((e1, e2) -> e2.getPontos().compareTo(e1.getPontos()));

        return ClassificacaoDTO.mostrarClassificacao(equipes);
    }

    public List<GrupoDTO> findByEsporte(Integer esporteId) {
        Esporte esporte = esporteRepository.findById(esporteId)
                .orElseThrow(() -> new RuntimeException("Esporte não encontrado."));

        List<Grupo> gruposList = grupoRepository.findByEsporte(esporte);
        List<GrupoDTO> dtoList = new ArrayList<>();
        for (Grupo g : gruposList) {
            dtoList.add(GrupoDTO.deModel(g));
        }
        return dtoList;
    }

    @Transactional
    public void deleteGrupos() {
        List<Grupo> grupos = grupoRepository.findAll();

        for (Grupo grupo : grupos) {
            for (Equipe equipe : grupo.getEquipes()) {
                equipe.setGrupo(null);
            }
            grupo.getEquipes().clear();
        }

        grupoRepository.deleteAll(grupos);
    }

    @Transactional
    public void deleteGrupoById(Integer id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado."));

        for (Equipe equipe : grupo.getEquipes()) {
            equipe.setGrupo(null);
        }

        grupo.getEquipes().clear();

        grupoRepository.delete(grupo);
    }

}