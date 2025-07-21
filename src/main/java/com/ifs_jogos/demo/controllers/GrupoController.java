package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.grupo.GrupoService;
import com.ifs_jogos.demo.services.grupo.dto.ClassificacaoDTO;
import com.ifs_jogos.demo.services.grupo.dto.GrupoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/grupo")
public class GrupoController {

    private final GrupoService grupoService;

    @PostMapping("/gerar/{esporteId}")
    public void gerarGrupos(@PathVariable("esporteId") Integer esporteId) {
        grupoService.gerarGrupos(esporteId);
    }

    @GetMapping
    public List<GrupoDTO> getGrupos() {
        return grupoService.getGrupos();
    }

    @GetMapping("/classificacao/{grupoId}")
    public List<ClassificacaoDTO> getClassificacao(@PathVariable("grupoId") Integer grupoId) {
        return grupoService.getClassificacao(grupoId);
    }

    @GetMapping("/esporte/{esporteId}")
    public List<GrupoDTO> findByEsporte(@PathVariable("esporteId") Integer esporteId) {
        return grupoService.findByEsporte(esporteId);
    }

    @DeleteMapping("/deleteAll")
    public void deleteGrupos() {
        grupoService.deleteGrupos();
    }

    @DeleteMapping("/delete/{grupoId}")
    public void deleteGrupoById(@PathVariable("grupoId") Integer grupoId) {
        grupoService.deleteGrupoById(grupoId);
    }

}