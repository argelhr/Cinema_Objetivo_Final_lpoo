package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository rep;

    public Sessao insert(Sessao sessao){
        Assert.isNull(sessao.getId(),"Erro ao gravar a sessao");
        return rep.save(sessao);
    }
}
