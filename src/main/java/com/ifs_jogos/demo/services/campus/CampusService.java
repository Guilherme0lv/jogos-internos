package com.ifs_jogos.demo.services.campus;

import com.ifs_jogos.demo.models.Campus;
import com.ifs_jogos.demo.repositories.CampusRepository;
import com.ifs_jogos.demo.services.campus.dto.CampusDTO;
import com.ifs_jogos.demo.services.campus.form.CampusForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampusService {

    private final CampusRepository campusRepository;

    @Transactional
    public CampusDTO createCampus(CampusForm form) {
        Campus campus = form.paraModel();
        return CampusDTO.deModel(campusRepository.save(campus));
    }

    public List<CampusDTO> getCampus() {
        List<Campus> campusList = campusRepository.findAll();

        List<CampusDTO> dtoList = new ArrayList<>();
        for (Campus c : campusList) {
            dtoList.add(CampusDTO.deModel(c));
        }
        return dtoList;
    }

    @Transactional
    public CampusDTO updateCampus(Integer id, CampusForm form) {
        Campus existente = campusRepository.findById(id).orElseThrow( () ->
                new RuntimeException("Campus não encontrado."));

        if (form.getCidade()!=null) existente.setCidade(form.getCidade());
        if (form.getNome()!=null) existente.setNome(form.getNome());

        return CampusDTO.deModel(campusRepository.save(existente));
    }

    @Transactional
    public void deleteCampus(Integer id) {
        Campus campus = campusRepository.findById(id).orElseThrow( () ->
                new RuntimeException("Campus não encontrado."));

        campusRepository.delete(campus);
    }
}
