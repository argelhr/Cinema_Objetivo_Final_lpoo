package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.Filme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired
    private SalaRepository rep;

    public Sala getSalaByID(Long id){

        Optional<Sala> optionalSala = rep.findById(id);
        if(optionalSala.isPresent()){
            return optionalSala.get();
        }
        return null;

    }
    public List<Sala> getSalas(){
        return new ArrayList<>(rep.findAll());
    }


    public Sala insertSala(Sala sala) {

        Assert.isNull(sala.getId(), "Não foi possivel realizar o cadastro");
        return rep.save(sala);

    }

    public List<Sala> getSalasBySituacao(boolean b) {
        return rep.getSalasBySituacao(b);
    }
    public Sala getSalaByIdAndSituacao(Long id,Boolean b) {
        return rep.getSalaByIdAndSituacao(id,b);
    }
}
