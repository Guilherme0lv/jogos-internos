package com.ifs_jogos.demo.repositories;

import com.ifs_jogos.demo.models.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer> {

    Campus findByNome(String nome);

}
