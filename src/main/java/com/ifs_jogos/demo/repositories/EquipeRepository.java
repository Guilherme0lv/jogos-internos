package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    Equipe findByNome(String nome);

    List<Equipe> findByCurso(Curso curso);

    List<Equipe> findByEsporte(Esporte esporte);

    List<Equipe> findByCampus(Campus campus);

    boolean existsByCursoAndEsporte(Curso curso, Esporte esporte);
    boolean existsByTecnicoAndEsporte(Usuario tecnico, Esporte esporte);

}
