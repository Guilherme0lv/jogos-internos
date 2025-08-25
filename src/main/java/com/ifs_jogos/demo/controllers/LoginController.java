package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.usuario.UsuarioService;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.LoginForm;
import com.ifs_jogos.demo.services.usuario.form.UsuarioForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioForm usuarioForm) {
        usuarioService.registerUsuario(usuarioForm);
        return ResponseEntity.ok("Usuario cadastrado com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm form) {
        UsuarioDTO autenticado = usuarioService.autenticar(form);

        if (autenticado != null) {
            return ResponseEntity.ok(autenticado);
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos.");
        }
    }
}
