package com.ifs_jogos.demo.repositories;


import com.ifs_jogos.demo.models.Curso;
import com.ifs_jogos.demo.models.CursoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    Curso findByNome(String nome);

    List<Curso> findByTipoCurso(CursoEnum tipoCurso);
}
