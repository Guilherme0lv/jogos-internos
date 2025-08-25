package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    Evento findByTipoEvento(String tipoEvento);
}
