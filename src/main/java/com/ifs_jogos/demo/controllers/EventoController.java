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
@RequestMapping("/eventos")
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

    @PatchMapping("/alterar/{eventoId}")
    public ResponseEntity<EventoDTO> updateEvento(@PathVariable("eventoId") Integer eventoId, @RequestBody EventoForm form) {
        EventoDTO dto = eventoService.updateEvento(eventoId, form);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/deletar/{eventoId}")
    public ResponseEntity<String> deleteEvento(@PathVariable("eventoId") Integer eventoId) {
        eventoService.deleteEvento(eventoId);
        return ResponseEntity.ok("Evento deletado com sucesso.");
    }
}
