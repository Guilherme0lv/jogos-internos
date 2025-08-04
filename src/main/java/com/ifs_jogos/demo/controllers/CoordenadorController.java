package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.usuario.CoordenadorService;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.CoordenadorForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/coord")
public class CoordenadorController {

    private final CoordenadorService coordenadorService;

    @PostMapping
    public ResponseEntity<String> createCoordenador(@RequestBody CoordenadorForm form) {
        coordenadorService.createCoordenador(form);
        return ResponseEntity.status(201).body("Coordenador cadastrado com sucesso");
    }

    @PatchMapping("/definir-tecnico/{usuarioId}")
    public ResponseEntity<UsuarioDTO> definirTecnico(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioDTO dto = coordenadorService.definirTecnico(usuarioId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getLogin/{coordenadorId}")
    public ResponseEntity<String> enviarLoginPorEmail(@PathVariable("coordenadorId") Integer coordenadorId) {
        String mensagem = coordenadorService.enviarSenhaPorEmail(coordenadorId);
        return ResponseEntity.ok(mensagem);
    }
}
