package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.Filme;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso.Ingresso;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sessao")
public class Sessao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Basic
    @Column(name = "dt_sessao")
    private LocalDate dtSessao;
    @Basic
    @Column(name = "hor_sessao")
    private LocalTime horSessao;
    @Basic
    @Column(name = "valor_inteira")
    private Double valorInteira;
    @Basic
    @Column(name = "valor_meia")
    private Double valorMeia;
    @Basic
    @Column(name = "encerrada")
    private Boolean encerrada;
    @OneToMany(mappedBy = "sessaoBySessao")
    private Collection<Ingresso> ingressosByCodSessao;
    @ManyToOne
    @JoinColumn(name = "cod_sala", referencedColumnName = "id", nullable = false)
    private Sala salaByCodSala;
    @ManyToOne
    @JoinColumn(name = "cod_filme", referencedColumnName = "id", nullable = false)
    private Filme filmeByCodFilme;
}
