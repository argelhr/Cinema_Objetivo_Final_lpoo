package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao,Long> {
    @Query(value = "SELECT s from Sessao s where s.dtSessao = ?1 and s.encerrada = ?2 and s.salaByCodSala = ?3 ")
    List<Sessao> getSessoesByDataAndSituacaoAndSala(LocalDate data, Boolean bool, Sala sala);
    @Query(value = "SELECT s from Sessao s where s.encerrada = ?1")
    List<Sessao> getSessoesBySituacao(Boolean bool);

    @Query(value = "SELECT s from Sessao s where s.id = ?1 and s.encerrada = ?2")
    Sessao findByIdAndSituacao(Long id, Boolean bool);


}
