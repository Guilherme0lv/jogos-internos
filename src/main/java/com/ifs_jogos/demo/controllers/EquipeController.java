package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.equipe.EquipeService;
import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import com.ifs_jogos.demo.services.equipe.form.EquipeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    @PostMapping
    public EquipeDTO createEquipe(@RequestBody EquipeForm equipeForm) {
        return equipeService.createEquipe(equipeForm);
    }

    @GetMapping
    public List<EquipeDTO> getEquipes() {
        return equipeService.getEquipes();
    }


    @GetMapping("/nome/{nome}")
    public EquipeDTO findByNome(@PathVariable("nome") String nome) {
        return equipeService.findByNome(nome);
    }

    @GetMapping("/curso/{idCurso}")
    public List<EquipeDTO> findByCurso(@PathVariable("idCurso") Integer idCurso) {
        return equipeService.findByCurso(idCurso);
    }

    @GetMapping("/esporte/{esporteId}")
    public List<EquipeDTO> findByEsporte(@PathVariable("esporteId") Integer esporteId) {
        return equipeService.findByEsporte(esporteId);
    }

    @GetMapping("/campus/{campusId}")
    public List<EquipeDTO> findByCampus(@PathVariable("campusId") Integer campusId) {
        return equipeService.findByCampus(campusId);
    }

    @PatchMapping("/update/{equipeId}")
    public EquipeDTO updateEquipe(@PathVariable("equipeId") Integer equipeId, @RequestBody EquipeForm equipeForm) {
        return equipeService.updateEquipe(equipeId, equipeForm);
    }

    @DeleteMapping("/delete/{id}")
    public EquipeDTO deleteEquipe(@PathVariable("id") Integer id) {
        return equipeService.deleteEquipe(id);
    }

}
