package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {
    @Autowired
    private FilmeRepository rep;

    public List<Filme> getFilmes() {
        return rep.findAll();
    }

    public Filme getFilmeById(long id) {
        Optional<Filme> filme = rep.findById(id);
        if (filme.isPresent())
            return filme.get();
        return null;
    }

    public List<Filme> getFilmeByTitulo(String titulo) {
        List<Filme> filmes = rep.findByTitulo('%' + titulo + '%');

        if(filmes.isEmpty()){
            return null;
        }
        return filmes;

    }

    public List<Filme> getFilmesBySituacao(Boolean status) {

        return rep.findFilmesBySituacao(status);
    }

    public Boolean alterar(Filme filme) {

        Assert.notNull(filme.getId(), "Não é possivel realizar o regitro com id nulo");

        Optional<Filme> f = rep.findById(filme.getId());
        if (f.isPresent()) {
            Filme db = f.get();
            db.setTitulo(filme.getTitulo());
            db.setDuracao(filme.getDuracao());
            rep.save(db);

            return true;
        }
        return false;

    }

    public Filme insert(Filme filme) {
        Assert.isNull(filme.getId(), "Não foi possivel inserir o registro");
        return rep.save(filme);
    }

    public Filme desativa(Long id) {

        Assert.notNull(id, "Não foi possivel realizar a operação");

        Optional<Filme> filme = rep.findById(id);
        if (filme.isPresent()) {
            Filme f = filme.get();
            f.setSituacao(false);
            rep.save(f);
            return f;
        }
        return null;
    }

    public Filme reativa(Long id) {
        Assert.notNull(id, "Não foi possível realizar a operação");

        Optional<Filme> filme = rep.findById(id);
        if (filme.isPresent()) {
            Filme f = filme.get();
            f.setSituacao(true);
            rep.save(f);
            return f;
        }
        return null;
    }
}
