package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ingresso")
public class Ingresso {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Basic
    @Column(name = "tipo")
    private Integer tipo;
    @ManyToOne
    @JoinColumn(name = "sessao", referencedColumnName = "id")
    private Sessao sessaoBySessao;

}
