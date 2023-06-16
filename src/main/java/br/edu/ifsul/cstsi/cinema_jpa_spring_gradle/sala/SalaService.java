package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

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

    public Sala getSalaByID(Long id) {

        Optional<Sala> optionalSala = rep.findById(id);
        if (optionalSala.isPresent()) {
            return optionalSala.get();
        }
        return null;

    }

    public List<Sala> getSalas() {

        ArrayList<Sala> salas = new ArrayList<>(rep.findAll());
        if (salas.isEmpty())
            return null;
        return salas;
    }

    public List<Sala> getSalasBySituacao(Boolean b) {

        return rep.getSalasBySituacao(b);
    }
    public Sala insertSala(Sala sala) {

        Assert.isNull(sala.getId(), "Não foi possivel realizar o cadastro");
        return rep.save(sala);

    }

    public Sala update(Sala s) {
        Assert.notNull(s.getId(), "Não foi possivel atualizar a sala");

        Optional<Sala> salaOptional = rep.findById(s.getId());
        if (salaOptional.isPresent()) {
            Sala sala = salaOptional.get();
            sala.setCapacidade(s.getCapacidade());
            return rep.save(sala);
        }
        return null;
    }
    public Sala reativar(Sala s){
        Assert.notNull(s.getId(),"Não é possivel reativar por ser nulo");
        Optional<Sala> salaOptional= rep.findById(s.getId());

        if(salaOptional.isPresent()) {
            Sala sala = salaOptional.get();
            sala.setStatus(true);
            return rep.save(sala);
        }
        return null;
    }
    public Sala desativar(Sala s){
        Assert.notNull(s.getId(),"Não é possivel reativar por ser nulo");
        Optional<Sala> salaOptional= rep.findById(s.getId());

        if(salaOptional.isPresent()) {
            Sala sala = salaOptional.get();
            sala.setStatus(false);
            return rep.save(sala);
        }
        return null;
    }
}
