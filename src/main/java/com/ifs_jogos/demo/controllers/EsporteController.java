package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import com.ifs_jogos.demo.services.esporte.EsporteService;
import com.ifs_jogos.demo.services.esporte.dto.EsporteDTO;
import com.ifs_jogos.demo.services.esporte.form.EsporteForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/esporte")
public class EsporteController {

    private final EsporteService esporteService;

    @PostMapping
    public ResponseEntity<EsporteDTO> createEsporte(@RequestBody EsporteForm form) {
        EsporteDTO dto = esporteService.createEsporte(form);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<EsporteDTO>> getEsportes() {
        List<EsporteDTO> esportes = esporteService.getEsportes();
        return ResponseEntity.ok(esportes);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<EsporteDTO> findByNome(@PathVariable("nome") String nome) {
        EsporteDTO dto = esporteService.findByNome(nome);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/campeao/{esporteId}")
    public ResponseEntity<EquipeDTO> getCampeao(@PathVariable("esporteId") Integer esporteId) {
        EquipeDTO campeao = esporteService.getCampeao(esporteId);
        return ResponseEntity.ok(campeao);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<EsporteDTO> updateEsporte(@PathVariable("id") Integer id, @RequestBody EsporteForm form) {
        EsporteDTO dto = esporteService.updateEsporte(id, form);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEsporte(@PathVariable("id") Integer id) {
        esporteService.deleteEsporte(id);
        return ResponseEntity.ok("Esporte removido com sucesso.");
    }
}
