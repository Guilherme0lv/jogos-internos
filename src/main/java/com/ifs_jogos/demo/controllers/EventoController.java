package com.ifs_jogos.demo.controllers;

import com.ifs_jogos.demo.services.evento.EventoService;
import com.ifs_jogos.demo.services.evento.dto.EventoDTO;
import com.ifs_jogos.demo.services.evento.form.EventoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public EventoDTO createEvento(@RequestBody EventoForm form) {
        return eventoService.createEvento(form);
    }

    @GetMapping
    public List<EventoDTO> getEventos() {
        return eventoService.getEventos();
    }

    @PatchMapping("/alterar/{eventoId}")
    public EventoDTO updateEvento(@PathVariable("eventoId") Integer eventoId, @RequestBody EventoForm form) {
        return eventoService.updateEvento(eventoId, form);
    }

    @DeleteMapping("/deletar/{eventoId}")
    public EventoDTO deleteEvento(@PathVariable("eventoId") Integer eventoId) {
        return eventoService.deleteEvento(eventoId);
    }
}
