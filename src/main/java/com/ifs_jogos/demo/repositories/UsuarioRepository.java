package com.ifs_jogos.demo.repositories;



import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByMatricula(String matricula);
    List<Usuario> findByNomeCompleto(String nome);
    List<Usuario> findByTipoUsuario(UsuarioEnum tipoUsuario);

    boolean existsByTipoUsuarioAndCurso_Id(UsuarioEnum tipoUsuario, Integer cursoId);

}
