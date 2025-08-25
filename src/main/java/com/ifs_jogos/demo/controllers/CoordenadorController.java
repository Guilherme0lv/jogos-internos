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

    @PutMapping("/definir-tecnico/{usuarioMatricula}")
    public ResponseEntity<UsuarioDTO> definirTecnico(@PathVariable("usuarioMatricula") String usuarioMatricula) {
        UsuarioDTO dto = coordenadorService.definirTecnico(usuarioMatricula);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/getLogin/{coordenadorMatricula}")
    public ResponseEntity<String> enviarLoginPorEmail(@PathVariable("coordenadorMatricula") String coordenadorMatricula) {
        String mensagem = coordenadorService.enviarSenhaPorEmail(coordenadorMatricula);
        return ResponseEntity.ok(mensagem);
    }
}
