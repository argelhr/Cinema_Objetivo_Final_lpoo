package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "filme")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Filme {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Basic
    @Column(name = "titulo")
    private String titulo;
    @Basic
    @Column(name = "duracao")
    private Integer duracao;
    @Basic
    @Column(name = "situacao")
    private Boolean situacao;
    @OneToMany(mappedBy = "filmeByCodFilme")
    private Collection<Sessao> sessaosByCodFilme;

    public String escreveTempo() {
        return (this.getDuracao() / 60 + "h " + this.getDuracao() % 60 + "min");
    }

    @Override
    public String toString() {
        return "Cod: " + id + " || Titulo: " + titulo + "  || Duração: " + escreveTempo();
    }
}
