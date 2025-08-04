package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.usuario.UsuarioService;
import com.ifs_jogos.demo.services.usuario.form.LoginForm;
import com.ifs_jogos.demo.services.usuario.form.UsuarioForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> login(@RequestBody LoginForm form) {
        boolean autenticado = usuarioService.autenticar(form);

        if (autenticado) {
            return ResponseEntity.ok("Login realizado com sucesso.");
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos.");
        }
    }
}
