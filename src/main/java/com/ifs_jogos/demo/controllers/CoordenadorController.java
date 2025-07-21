package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.usuario.CoordenadorService;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.CoordenadorForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/coord")
public class CoordenadorController {

    private final CoordenadorService coordenadorService;

    @PostMapping
    public UsuarioDTO createCoordenador(@RequestBody CoordenadorForm form) {
        return coordenadorService.createCoordenador(form);
    }

    @PatchMapping("/definir-tecnico/{usuarioId}")
    public UsuarioDTO definirTecnico(@PathVariable("usuarioId") Integer usuarioId) {
        return coordenadorService.definirTecnico(usuarioId);
    }

    @GetMapping("/getLogin/{coordenadorId}")
    public String enviarLoginPorEmail(@PathVariable("coordenadorId") Integer coordenadorId) {
        return coordenadorService.enviarSenhaPorEmail(coordenadorId);
    }
}
