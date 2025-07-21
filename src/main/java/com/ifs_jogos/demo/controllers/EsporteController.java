package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import com.ifs_jogos.demo.services.esporte.EsporteService;
import com.ifs_jogos.demo.services.esporte.dto.EsporteDTO;
import com.ifs_jogos.demo.services.esporte.form.EsporteForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/esporte")
public class EsporteController {

    private final EsporteService esporteService;

    @PostMapping
    public EsporteDTO createEsporte(@RequestBody EsporteForm form) {
        return esporteService.createEsporte(form);
    }

    @GetMapping
    public List<EsporteDTO> getEsportes() {
        return esporteService.getEsportes();
    }

    @GetMapping("/nome/{nome}")
    public EsporteDTO findByNome(@PathVariable("nome") String nome) {
        return esporteService.findByNome(nome);
    }

    @PatchMapping("/campeao/{esporteId}")
    public EquipeDTO getCampeao(@PathVariable("esporteId") Integer esporteId) {
        return esporteService.getCampeao(esporteId);
    }

    @PatchMapping("/update/{id}")
    public EsporteDTO updateEsporte(@PathVariable("id") Integer id, @RequestBody EsporteForm form) {
        return esporteService.updateEsporte(id, form);
    }

    @DeleteMapping("/delete/{id}")
    public EsporteDTO deleteEsporte(@PathVariable("id") Integer id) {
        return esporteService.deleteEsporte(id);
    }

}
