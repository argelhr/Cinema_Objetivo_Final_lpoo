package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngressoRepository extends JpaRepository<Ingresso,Long> {
    @Query(value = "SELECT count (i) from  Ingresso i where i.sessaoBySessao = ?1")
    Integer getIngressosBySessao(Sessao s);

}
