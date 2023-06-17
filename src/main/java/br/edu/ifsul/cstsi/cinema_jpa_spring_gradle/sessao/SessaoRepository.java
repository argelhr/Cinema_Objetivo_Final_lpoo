package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.Filme;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao,Long> {
    @Query(value = "SELECT s from Sessao s where s.dtSessao = ?1 and s.encerrada = ?2 and s.salaByCodSala = ?3 ")
    List<Sessao> getSessoesByDataAndSituacaoAndSala(LocalDate data, Boolean bool, Sala sala);
    @Query(value = "SELECT s from Sessao s where s.encerrada = ?1")
    List<Sessao> getSessoesBySituacao(Boolean bool);
    @Query(value = "SELECT s from Sessao s where s.filmeByCodFilme = ?1 and s.encerrada = false")
    List<Sessao> getSessoesByFilme(Filme filme);
    @Query(value = "SELECT s from Sessao s where s.salaByCodSala = ?1 and s.encerrada = false")
    List<Sessao> getSessaoBySala(Sala sala);


}
