package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.campus.CampusService;
import com.ifs_jogos.demo.services.campus.dto.CampusDTO;
import com.ifs_jogos.demo.services.campus.form.CampusForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/campus")
public class CampusController {

    private final CampusService campusService;

    @PostMapping
    public ResponseEntity<CampusDTO> createCampus(@RequestBody CampusForm campus) {
        CampusDTO created = campusService.createCampus(campus);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CampusDTO>> getCampus() {
        List<CampusDTO> list = campusService.getCampus();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/update/{nome}")
    public ResponseEntity<CampusDTO> updateCampus(@PathVariable("nome") String nome, @RequestBody CampusForm form) {
        CampusDTO updated = campusService.updateCampus(nome, form);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{nome}")
    public ResponseEntity<String> deleteCampus(@PathVariable("nome") String nome) {
        campusService.deleteCampus(nome);
        return ResponseEntity.ok("Campus deletado com sucesso.");
    }
}
