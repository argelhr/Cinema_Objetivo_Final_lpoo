package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
@Service
public class SalaService {
    @Autowired
    private SalaRepository rep;

    public Sala insertSala(Sala sala) {
//        System.out.println(sala);
//        Assert.isNull(sala.getId(), "Não foi possivel realizar o cadastro");
        return rep.save(sala);

    }

}
