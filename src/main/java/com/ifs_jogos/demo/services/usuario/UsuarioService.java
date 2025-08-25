package com.ifs_jogos.demo.services.usuario;

import com.ifs_jogos.demo.models.Curso;
import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;
import com.ifs_jogos.demo.repositories.CursoRepository;
import com.ifs_jogos.demo.repositories.UsuarioRepository;
import com.ifs_jogos.demo.services.usuario.form.LoginForm;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.UsuarioForm;
import com.ifs_jogos.demo.util.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public void registerUsuario(UsuarioForm form) {
        Curso curso = cursoRepository.findByNome(form.getCursoNome());

        if (curso==null) {
            throw new RuntimeException("Curso não encontrado.");
        }
        Usuario usuario = form.paraModel(curso);

        String senhaHash = PasswordUtils.encrypt(form.getSenha());
        usuario.setSenha(senhaHash);
        usuarioRepository.save(usuario);
    }

    public UsuarioDTO autenticar(LoginForm login) {
        Usuario usuario = usuarioRepository.findByMatricula(login.getMatricula());
        if (usuario!=null && PasswordUtils.compararSenha(login.getSenha(), usuario.getSenha())) {
            return UsuarioDTO.deModel(usuario);
        } else {
            return null;
        }
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        
        List<UsuarioDTO> dtoList = new ArrayList<>();
        for (Usuario u : usuarioList) {
            dtoList.add(UsuarioDTO.deModel(u));
        }
        return dtoList;
    }

    public UsuarioDTO findByMatricula(String matricula) {
        Usuario usuario = usuarioRepository.findByMatricula(matricula);

        if (usuario==null) {
            throw new RuntimeException("Usuario não encontrado.");
        }

        return UsuarioDTO.deModel(usuario);
    }

    public List<UsuarioDTO> findByNomeCompleto(String nomeCompleto) {
        List<Usuario> usuarioList = usuarioRepository.findByNomeCompleto(nomeCompleto);

        List<UsuarioDTO> dtoList = new ArrayList<>();
        for (Usuario u : usuarioList) {
            dtoList.add(UsuarioDTO.deModel(u));
        }
        return dtoList;
    }

    public List<UsuarioDTO> findByTipoUsuario(String tipoUsuario) {
        UsuarioEnum tipoU = UsuarioEnum.valueOf(tipoUsuario.toUpperCase());

        List<Usuario> usuarioList = usuarioRepository.findByTipoUsuario(tipoU);

        List<UsuarioDTO> dtoList = new ArrayList<>();
        for (Usuario u : usuarioList) {
            dtoList.add(UsuarioDTO.deModel(u));
        }
        return dtoList;
    }

    @Transactional
    public UsuarioDTO updateUsuario(String matricula, UsuarioForm atualizado) {
        Usuario existente = usuarioRepository.findByMatricula(matricula);
        if (existente==null) {
            throw new RuntimeException("Usuario não encontrado");
        }

        if (atualizado.getMatricula() != null) existente.setMatricula(atualizado.getMatricula());

        if (atualizado.getSenha() != null) {
            String senhaHash = PasswordUtils.encrypt(atualizado.getSenha());
            existente.setSenha(senhaHash);
        }

        if (atualizado.getNomeCompleto() != null) existente.setNomeCompleto(atualizado.getNomeCompleto());

        if (atualizado.getApelido() != null) existente.setApelido(atualizado.getApelido());

        if (atualizado.getTelefone() != null) existente.setTelefone(atualizado.getTelefone());

        if (atualizado.getCursoNome() != null) {
            Curso curso = cursoRepository.findByNome(atualizado.getCursoNome());
            if (curso==null) {
                throw new RuntimeException("Curso não encontrado.");
            }
            existente.setCurso(curso);
        }

        return UsuarioDTO.deModel(usuarioRepository.save(existente));
    }

    @Transactional
    public UsuarioDTO deleteUsuario(String matricula) {
        Usuario usuario = usuarioRepository.findByMatricula(matricula);

        if (usuario==null) {
            throw new RuntimeException("Usuario não encontrado.");
        }

        usuarioRepository.delete(usuario);
        return UsuarioDTO.deModel(usuario);
    }

}
