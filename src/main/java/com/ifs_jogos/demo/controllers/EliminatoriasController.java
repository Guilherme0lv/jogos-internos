package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.eliminatorias.EliminatoriasService;
import com.ifs_jogos.demo.services.eliminatorias.dto.EliminatoriasDTO;
import com.ifs_jogos.demo.services.eliminatorias.form.FaseForm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eliminatorias")
public class EliminatoriasController {

    private final EliminatoriasService eliminatoriasService;

    @PostMapping("/gerar/{esporteId}")
    public void gerarEliminatorias(@PathVariable("esporteId") Integer esporteId) {
        eliminatoriasService.gerarEliminatorias(esporteId);
    }

    @GetMapping("/fase/jogos")
    public EliminatoriasDTO getEliminatorias(@RequestBody FaseForm form) {
        return eliminatoriasService.getEliminatorias(form);
    }


    @PostMapping("/gerar/proxima-fase")
    public void gerarProximaFase(@RequestBody FaseForm form) {
        eliminatoriasService.gerarProximaFase(form);
    }
}
