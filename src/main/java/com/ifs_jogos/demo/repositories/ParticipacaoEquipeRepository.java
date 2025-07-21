package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.Equipe;
import com.ifs_jogos.demo.models.ParticipacaoEquipe;
import com.ifs_jogos.demo.models.ParticipacaoEquipeId;
import com.ifs_jogos.demo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoEquipeRepository extends JpaRepository<ParticipacaoEquipe, ParticipacaoEquipeId> {

    boolean existsByUsuario(Usuario usuario);
    void deleteByEquipe(Equipe equipe);
}
