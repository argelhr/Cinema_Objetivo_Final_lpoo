package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sala")
public class Sala {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Basic
    @Column(name = "capacidade")
    private Integer capacidade;

    @Basic
    @Column(name = "status")
    private Boolean status;
    @OneToMany(mappedBy = "salaByCodSala")
    private Collection<Sessao> sessaosByCodSala;

    @Override
    public String toString() {
        return "Sala[id:" + id +" | capacidade: " + capacidade +
                " | status: " + (status?"Ativo":"Desativado") +
                "]";
    }
}
