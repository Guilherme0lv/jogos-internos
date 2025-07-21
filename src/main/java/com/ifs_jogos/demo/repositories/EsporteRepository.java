package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.Esporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsporteRepository extends JpaRepository<Esporte, Integer> {
    Esporte findByNome(String nome);
}
