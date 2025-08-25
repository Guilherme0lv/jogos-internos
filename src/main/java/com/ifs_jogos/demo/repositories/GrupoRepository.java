package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.Esporte;
import com.ifs_jogos.demo.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    List<Grupo> findByEsporte(Esporte esporte);
    Grupo findByNome(String nome);

    void deleteGrupoByEsporte(Esporte esporte);

}
