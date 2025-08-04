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

    @PatchMapping("/update/{id}")
    public ResponseEntity<CampusDTO> updateCampus(@PathVariable("id") Integer id, @RequestBody CampusForm form) {
        CampusDTO updated = campusService.updateCampus(id, form);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteCampus(@PathVariable("id") Integer id) {
        campusService.deleteCampus(id);
        return ResponseEntity.ok("Campus deletado com sucesso.");
    }
}
