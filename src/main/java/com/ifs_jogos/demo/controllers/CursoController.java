package com.ifs_jogos.demo.controllers;


import com.ifs_jogos.demo.services.curso.CursoService;
import com.ifs_jogos.demo.services.curso.dto.CursoDTO;
import com.ifs_jogos.demo.services.curso.form.CursoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/curso")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public CursoDTO createCurso(@RequestBody CursoForm form) {
        return cursoService.createCurso(form);
    }

    @GetMapping
    public List<CursoDTO> getCursos() {
        return cursoService.getCursos();
    }

    @GetMapping("/nome/{nome}")
    public CursoDTO findByNome(@PathVariable("nome") String nome) {
        return cursoService.findByNome(nome);
    }

    @GetMapping("/tipo/{tipoCurso}")
    public List<CursoDTO> findByTipoCurso(@PathVariable("tipoCurso") String tipoCurso) {
        return cursoService.findByTipoCurso(tipoCurso);
    }

    @PatchMapping("/update/{id}")
    public CursoDTO updateCurso(@PathVariable("id") Integer id, @RequestBody CursoForm form) {
        return cursoService.updateCurso(id, form);
    }

    @DeleteMapping("/id/{id}")
    public CursoDTO deleteCurso(@PathVariable("id") Integer id) {
        return cursoService.deleteCurso(id);
    }
}
