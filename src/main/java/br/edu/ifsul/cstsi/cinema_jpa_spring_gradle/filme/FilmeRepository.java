package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme,Long> {

    @Query(value = "SELECT f FROM Filme f where f.situacao = ?1")
    List<Filme> findFilmesBySituacao (Boolean situacao);

    @Query(value = "SELECT f FROM Filme f where f.id = ?1 and f.situacao = ?2")
    Filme findFilmesByIdAndSituacao(Long id, Boolean status);


}
