package com.ifs_jogos.demo.services.usuario;

import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;
import com.ifs_jogos.demo.repositories.UsuarioRepository;
import com.ifs_jogos.demo.services.usuario.dto.UsuarioDTO;
import com.ifs_jogos.demo.services.usuario.form.CoordenadorForm;
import com.ifs_jogos.demo.services.usuario.form.UsuarioForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioDTO createUsuario(UsuarioForm form) {
       Usuario usuario = usuarioRepository.save(form.paraModel());
       return UsuarioDTO.deModel(usuario);
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
    public UsuarioDTO updateUsuario(Integer id, UsuarioForm atualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Usuario não encontrado."));

        if (atualizado.getMatricula() != null) existente.setMatricula(atualizado.getMatricula());

        if (atualizado.getSenha() != null) existente.setSenha(atualizado.getSenha());

        if (atualizado.getNomeCompleto() != null) existente.setNomeCompleto(atualizado.getNomeCompleto());

        if (atualizado.getApelido() != null) existente.setApelido(atualizado.getApelido());

        if (atualizado.getTelefone() != null) existente.setTelefone(atualizado.getTelefone());

        return UsuarioDTO.deModel(usuarioRepository.save(existente));
    }

    @Transactional
    public UsuarioDTO deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Usuario não encontrado."));

        usuarioRepository.delete(usuario);
        return UsuarioDTO.deModel(usuario);
    }

}
