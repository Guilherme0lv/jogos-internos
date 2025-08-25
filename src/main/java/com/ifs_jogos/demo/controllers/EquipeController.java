package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.equipe.EquipeService;
import com.ifs_jogos.demo.services.equipe.dto.EquipeDTO;
import com.ifs_jogos.demo.services.equipe.form.EquipeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    @PostMapping
    public ResponseEntity<EquipeDTO> createEquipe(@RequestBody EquipeForm equipeForm) {
        EquipeDTO dto = equipeService.createEquipe(equipeForm);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> getEquipes() {
        List<EquipeDTO> equipes = equipeService.getEquipes();
        return ResponseEntity.ok(equipes);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<EquipeDTO> findByNome(@PathVariable("nome") String nome) {
        EquipeDTO dto = equipeService.findByNome(nome);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<EquipeDTO>> findByCurso(@PathVariable("idCurso") Integer idCurso) {
        List<EquipeDTO> equipes = equipeService.findByCurso(idCurso);
        return ResponseEntity.ok(equipes);
    }

    @GetMapping("/buscar-por-esporte/{nomeEsporte}")
    public ResponseEntity<List<EquipeDTO>> findByEsporte(@PathVariable("nomeEsporte") String nomeEsporte) {
        List<EquipeDTO> equipes = equipeService.findByEsporte(nomeEsporte);
        return ResponseEntity.ok(equipes);
    }

    @GetMapping("/campus/{campusId}")
    public ResponseEntity<List<EquipeDTO>> findByCampus(@PathVariable("campusId") Integer campusId) {
        List<EquipeDTO> equipes = equipeService.findByCampus(campusId);
        return ResponseEntity.ok(equipes);
    }

    @PutMapping("/update/{nome}")
    public ResponseEntity<EquipeDTO> updateEquipe(@PathVariable("nome") String nome, @RequestBody EquipeForm equipeForm) {
        EquipeDTO dto = equipeService.updateEquipe(nome, equipeForm);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{nomeEquipe}")
    public ResponseEntity<String> deleteEquipe(@PathVariable("nomeEquipe") String nomeEquipe) {
        equipeService.deleteEquipe(nomeEquipe);
        return ResponseEntity.ok("Equipe removida com sucesso.");
    }

}
