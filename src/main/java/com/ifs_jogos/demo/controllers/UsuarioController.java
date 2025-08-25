package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.usuario.UsuarioService;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.UsuarioForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping("/matricula/{matricula}")
    public UsuarioDTO findByMatricula(@PathVariable("matricula") String matricula) {
        return usuarioService.findByMatricula(matricula);
    }

    @GetMapping("/nome/{nome}")
    public List<UsuarioDTO> findByNomeCompleto(@PathVariable("nome") String nome) {
        return usuarioService.findByNomeCompleto(nome);
    }

    @GetMapping("/tipo/{tipoUsuario}")
    public List<UsuarioDTO> findByTipoUsuario(@PathVariable("tipoUsuario") String tipoUsuario) {
        return usuarioService.findByTipoUsuario(tipoUsuario);
    }

    @PutMapping("/update/{matricula}")
    public UsuarioDTO updateUsuario(@PathVariable("matricula") String matricula, @RequestBody UsuarioForm form) {
        return usuarioService.updateUsuario(matricula, form);
    }

    @DeleteMapping("/delete/{matricula}")
    public UsuarioDTO deleteUsuario(@PathVariable("matricula") String matricula) {
        return usuarioService.deleteUsuario(matricula);
    }

}
