package com.ifs_jogos.demo.services.curso;


import com.ifs_jogos.demo.models.Curso;
import com.ifs_jogos.demo.models.CursoEnum;
import com.ifs_jogos.demo.models.Usuario;
import com.ifs_jogos.demo.models.UsuarioEnum;
import com.ifs_jogos.demo.repositories.CursoRepository;
import com.ifs_jogos.demo.repositories.UsuarioRepository;
import com.ifs_jogos.demo.services.curso.dto.CursoDTO;
import com.ifs_jogos.demo.services.curso.form.CursoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CursoDTO createCurso(CursoForm form) {
        Curso curso = cursoRepository.save(form.paraModel());

        return CursoDTO.deModel(curso);
    }


    public List<CursoDTO> getCursos() {
        List<Curso> cursoList = cursoRepository.findAll();

        List<CursoDTO> dtoList = new ArrayList<>();
        for (Curso c : cursoList) {
            dtoList.add(CursoDTO.deModel(c));
        }
        return dtoList;
    }


    public CursoDTO findByNome(String nome) {
        Curso curso = cursoRepository.findByNome(nome);
        return CursoDTO.deModel(curso);
    }


    public List<CursoDTO> findByTipoCurso(String tipoCurso) {
        CursoEnum tipoC = CursoEnum.valueOf(tipoCurso.toUpperCase());

        List<Curso> cursoList = cursoRepository.findByTipoCurso(tipoC);

        List<CursoDTO> dtoList = new ArrayList<>();
        for(Curso c : cursoList) {
            dtoList.add(CursoDTO.deModel(c));
        }
        return dtoList;
    }


    @Transactional
    public CursoDTO updateCurso(Integer id, CursoForm form) {
        Curso existente = cursoRepository.findById(id).orElseThrow( () ->
                new RuntimeException("Curso não encontrado."));

        if (form.getNome() != null) existente.setNome(form.getNome());
        if (form.getTipoCurso()!=null) existente.setTipoCurso(form.getTipoCurso());

        return CursoDTO.deModel(cursoRepository.save(existente));
    }

    @Transactional
    public CursoDTO deleteCurso(Integer id) {
        Curso curso = cursoRepository.findById(id).orElseThrow( () ->
                new RuntimeException("Curso não encontrado."));

        cursoRepository.delete(curso);
        return CursoDTO.deModel(curso);
    }

}
