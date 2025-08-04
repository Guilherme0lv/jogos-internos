package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.grupo.GrupoService;
import com.ifs_jogos.demo.services.grupo.dto.ClassificacaoDTO;
import com.ifs_jogos.demo.services.grupo.dto.GrupoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/grupo")
public class GrupoController {

    private final GrupoService grupoService;

    @PostMapping("/gerar/{esporteId}")
    public ResponseEntity<String> gerarGrupos(@PathVariable("esporteId") Integer esporteId) {
        grupoService.gerarGrupos(esporteId);
        return ResponseEntity.status(201).body("Grupos gerados com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> getGrupos() {
        List<GrupoDTO> grupos = grupoService.getGrupos();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/classificacao/{grupoId}")
    public ResponseEntity<List<ClassificacaoDTO>> getClassificacao(@PathVariable("grupoId") Integer grupoId) {
        List<ClassificacaoDTO> classificacao = grupoService.getClassificacao(grupoId);
        return ResponseEntity.ok(classificacao);
    }

    @GetMapping("/esporte/{esporteId}")
    public ResponseEntity<List<GrupoDTO>> findByEsporte(@PathVariable("esporteId") Integer esporteId) {
        List<GrupoDTO> grupos = grupoService.findByEsporte(esporteId);
        return ResponseEntity.ok(grupos);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteGrupos() {
        grupoService.deleteGrupos();
        return ResponseEntity.ok("Todos os grupos foram deletados com sucesso.");
    }

    @DeleteMapping("/delete/{grupoId}")
    public ResponseEntity<String> deleteGrupoById(@PathVariable("grupoId") Integer grupoId) {
        grupoService.deleteGrupoById(grupoId);
        return ResponseEntity.ok("Grupo deletado com sucesso.");
    }
}