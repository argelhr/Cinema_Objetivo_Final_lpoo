package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    @Query(value = "SELECT s from  Sala s where s.status = ?1")
    List<Sala> getSalasBySituacao(Boolean status);
    @Query(value = "SELECT s from Sala s where s.id = ?1 and s.status = ?2")
    Sala getSalaByIdAndSituacao(Long id,Boolean b);

}
