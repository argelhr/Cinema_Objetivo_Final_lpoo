package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository rep;

    public Sessao getSessaoById(Long id){

        Optional<Sessao> sessao = rep.findById(id);
        if(sessao.isPresent())
            return sessao.get();
        return null;

    }
    public List<Sessao> getSessoesByDataAndSituacaoAndSala(LocalDate data, Boolean bool, Sala sala){
        List<Sessao> sessoes= rep.getSessoesByDataAndSituacaoAndSala(data, bool, sala);
        return sessoes;
    }
    public List<Sessao> getSessoesBySituacao(Boolean bool){
        List<Sessao> sessaoList = rep.getSessoesBySituacao(bool);
        return sessaoList;
    }

    public Sessao insert(Sessao sessao) {
        Assert.isNull(sessao.getId(), "Erro ao gravar a sessao");
        return rep.save(sessao);
    }

    public Sessao disable(Sessao sessao){
        sessao.setEncerrada(true);
        return rep.save(sessao);
    }


}
