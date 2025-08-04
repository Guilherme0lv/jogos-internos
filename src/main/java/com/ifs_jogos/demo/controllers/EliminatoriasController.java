package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.eliminatorias.EliminatoriasService;
import com.ifs_jogos.demo.services.eliminatorias.dto.EliminatoriasDTO;
import com.ifs_jogos.demo.services.eliminatorias.form.FaseForm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eliminatorias")
public class EliminatoriasController {

    private final EliminatoriasService eliminatoriasService;

    @PostMapping("/gerar/{esporteId}")
    public ResponseEntity<String> gerarEliminatorias(@PathVariable("esporteId") Integer esporteId) {
        eliminatoriasService.gerarEliminatorias(esporteId);
        return ResponseEntity.status(201).body("Fase eliminatória gerada com sucesso.");
    }

    @GetMapping("/fase/jogos")
    public ResponseEntity<EliminatoriasDTO> getEliminatorias(@RequestBody FaseForm form) {
        EliminatoriasDTO dto = eliminatoriasService.getEliminatorias(form);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/gerar/proxima-fase")
    public ResponseEntity<String> gerarProximaFase(@RequestBody FaseForm form) {
        eliminatoriasService.gerarProximaFase(form);
        return ResponseEntity.status(201).body("Próxima fase eliminatória gerada com sucesso.");
    }
}
