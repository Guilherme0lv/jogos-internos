package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.Esporte;
import com.ifs_jogos.demo.models.FaseEnum;
import com.ifs_jogos.demo.models.Grupo;
import com.ifs_jogos.demo.models.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {

    List<Jogo> findByFinalizadoTrue();

    List<Jogo> findByGrupo(Grupo grupo);

    @Query(value = """
    SELECT j.* FROM jogo j
    JOIN grupo g ON j.grupo_id = g.id
    WHERE g.esporte_id = :esporteId
    ORDER BY j.data_hora DESC
    LIMIT 1
    """, nativeQuery = true)
    Jogo buscarUltimoJogoPorGrupo(@Param("esporteId") Integer esporteId);

    @Query(value = """
    SELECT j.* FROM jogo j
    JOIN equipe ea ON j.equipe_a_id = ea.id
    JOIN equipe eb ON j.equipe_b_id = eb.id
    WHERE ea.esporte_id = :esporteId OR eb.esporte_id = :esporteId
    ORDER BY j.data_hora DESC
    LIMIT 1
    """, nativeQuery = true)
    Jogo buscarUltimoJogoPorEsporte(@Param("esporteId") Integer esporteId);

    List<Jogo> findByFaseAndEquipeA_Esporte(FaseEnum fase, Esporte esporte);

    @Query("""
    SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END
    FROM Jogo j
    WHERE j.grupo.esporte = :esporte AND j.grupo IS NOT NULL AND j.finalizado = false
    """)
    boolean existeJogoDeGrupoNaoFinalizado(@Param("esporte") Esporte esporte);

    boolean existsByFaseAndFinalizadoFalseAndEquipeA_Esporte(FaseEnum fase, Esporte esporte);


}
