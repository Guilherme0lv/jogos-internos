package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.evento.EventoService;
import com.ifs_jogos.demo.services.evento.dto.EventoDTO;
import com.ifs_jogos.demo.services.evento.form.EventoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;
    @PostMapping
    public ResponseEntity<EventoDTO> createEvento(@RequestBody EventoForm form) {
        EventoDTO dto = eventoService.createEvento(form);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getEventos() {
        List<EventoDTO> eventos = eventoService.getEventos();
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/update/{tipoEvento}")
    public ResponseEntity<EventoDTO> updateEvento(@PathVariable("tipoEvento") String tipoEvento, @RequestBody EventoForm form) {
        EventoDTO dto = eventoService.updateEvento(tipoEvento, form);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{tipoEvento}")
    public ResponseEntity<String> deleteEvento(@PathVariable("tipoEvento") String tipoEvento) {
        eventoService.deleteEvento(tipoEvento);
        return ResponseEntity.ok("Evento deletado com sucesso.");
    }
}
