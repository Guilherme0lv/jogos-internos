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

    @PostMapping("/gerar/{esporteNome}")
    public ResponseEntity<List<GrupoDTO>> gerarGrupos(@PathVariable("esporteNome") String esporteNome) {
        List<GrupoDTO> dto = grupoService.gerarGrupos(esporteNome);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> getGrupos() {
        List<GrupoDTO> grupos = grupoService.getGrupos();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/classificacao/{grupoNome}")
    public ResponseEntity<List<ClassificacaoDTO>> getClassificacao(@PathVariable("grupoNome") String grupoNome) {
        List<ClassificacaoDTO> classificacao = grupoService.getClassificacao(grupoNome);
        return ResponseEntity.ok(classificacao);
    }

    @GetMapping("/esporte/{nomeEsporte}")
    public ResponseEntity<List<GrupoDTO>> findByEsporte(@PathVariable("nomeEsporte") String nomeEsporte) {
        List<GrupoDTO> grupos = grupoService.findByEsporte(nomeEsporte);
        return ResponseEntity.ok(grupos);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteGrupos() {
        grupoService.deleteGrupos();
        return ResponseEntity.ok("Todos os grupos foram deletados com sucesso.");
    }

    @DeleteMapping("/delete-by-esporte/{esporteNome}")
    public ResponseEntity<String> deleteByEsporte(@PathVariable("esporteNome") String esporteNome) {
        grupoService.deleteGrupoByEsporte(esporteNome);
        return ResponseEntity.ok("Grupos deletados com sucesso.");
    }

    @DeleteMapping("/delete/{grupoId}")
    public ResponseEntity<String> deleteGrupoById(@PathVariable("grupoId") Integer grupoId) {
        grupoService.deleteGrupoById(grupoId);
        return ResponseEntity.ok("Grupo deletado com sucesso.");
    }
}