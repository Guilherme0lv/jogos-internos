package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.curso.CursoService;
import com.ifs_jogos.demo.services.curso.dto.CursoDTO;
import com.ifs_jogos.demo.services.curso.form.CursoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/curso")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoDTO> createCurso(@RequestBody CursoForm form) {
        CursoDTO dto = cursoService.createCurso(form);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> getCursos() {
        List<CursoDTO> cursos = cursoService.getCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<CursoDTO> findByNome(@PathVariable("nome") String nome) {
        CursoDTO dto = cursoService.findByNome(nome);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/tipo/{tipoCurso}")
    public ResponseEntity<List<CursoDTO>> findByTipoCurso(@PathVariable("tipoCurso") String tipoCurso) {
        List<CursoDTO> cursos = cursoService.findByTipoCurso(tipoCurso);
        return ResponseEntity.ok(cursos);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CursoDTO> updateCurso(@PathVariable("id") Integer id, @RequestBody CursoForm form) {
        CursoDTO dto = cursoService.updateCurso(id, form);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<CursoDTO> deleteCurso(@PathVariable("id") Integer id) {
        CursoDTO dto = cursoService.deleteCurso(id);
        return ResponseEntity.ok(dto);
    }
}
