package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Integer> {


    //procura por STATUS e ESPORTE
    List<Jogo> findByStatusAndGrupo_Esporte(JogoStatusEnum status, Esporte esporte);

    //procura por FASE e ESPORTE
    List<Jogo> findByFaseAndEquipeA_Esporte(FaseEnum fase, Esporte esporte);

    //verifica se há jogos por FASE, STATUS e ESPORTE
    @Query("""
    SELECT COUNT(j) > 0 FROM Jogo j
    WHERE j.fase = :fase
    AND j.status = :status
    AND (j.equipeA.esporte = :esporte OR j.equipeB.esporte = :esporte)
    """)
    boolean existeJogoEliminatorioPendente(@Param("fase") FaseEnum fase,
                                           @Param("status") JogoStatusEnum status,
                                           @Param("esporte") Esporte esporte);

    List<Jogo> findByGrupo(Grupo grupo);

    //verifica se há jogos de eliminatorias
    @Query("""
    SELECT COUNT(j) > 0 FROM Jogo j
    WHERE j.fase IN ('OITAVASFINAL', 'QUARTASFINAL', 'SEMIFINAL', 'FINAL')
    AND j.equipeA.esporte = :esporte
    """)
    boolean existsEliminatoria(@Param("esporte") Esporte esporte);

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



}
