package com.ifs_jogos.demo.services.evento;


import com.ifs_jogos.demo.models.Evento;
import com.ifs_jogos.demo.repositories.EventoRepository;
import com.ifs_jogos.demo.services.evento.dto.EventoDTO;
import com.ifs_jogos.demo.services.evento.form.EventoForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional
    public EventoDTO createEvento(EventoForm form) {
        Evento evento = form.paraModel();

        return EventoDTO.deModel(eventoRepository.save(evento));
    }

    public List<EventoDTO> getEventos() {
        List<Evento> eventos =  eventoRepository.findAll();

        List<EventoDTO > dtoList = new ArrayList<>();
        for (Evento e : eventos) {
            dtoList.add(EventoDTO.deModel(e));
        }
        return dtoList;
    }

    @Transactional
    public EventoDTO updateEvento(Integer eventoId, EventoForm form) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        if(form.getTipoEvento()!=null) evento.setTipoEvento(form.getTipoEvento());
        if(form.getDataInicio()!=null) evento.setDataInicio(form.getDataInicio());
        if(form.getDataFim()!=null) evento.setDataFim(form.getDataFim());

        eventoRepository.save(evento);
        return EventoDTO.deModel(evento);
    }

    @Transactional
    public EventoDTO deleteEvento(Integer eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));
        eventoRepository.delete(evento);

        return EventoDTO.deModel(eventoRepository.save(evento));
    }

}
