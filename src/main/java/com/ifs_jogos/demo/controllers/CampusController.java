package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.campus.CampusService;
import com.ifs_jogos.demo.services.campus.dto.CampusDTO;
import com.ifs_jogos.demo.services.campus.form.CampusForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/campus")
public class CampusController {

    private final CampusService campusService;

    @PostMapping
    public CampusDTO createCampus(@RequestBody CampusForm campus) {
        return campusService.createCampus(campus);
    }

    @GetMapping
    public List<CampusDTO> getCampus() {
        return campusService.getCampus();
    }

    @PatchMapping("/update/{id}")
    public CampusDTO updateCampus(@PathVariable("id") Integer id, @RequestBody CampusForm form) {
        return campusService.updateCampus(id, form);
    }

    @DeleteMapping("/id/{id}")
    public CampusDTO deleteCampus(@PathVariable("id") Integer id) {
        return campusService.deleteCampus(id);
    }
}
