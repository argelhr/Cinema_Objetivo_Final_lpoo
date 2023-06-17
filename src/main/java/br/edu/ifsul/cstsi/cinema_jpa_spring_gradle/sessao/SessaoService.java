package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.Filme;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository rep;

    public List<Sessao> getSessoesByDataAndSituacaoAndSala(LocalDate data, Boolean bool, Sala sala) {
        return rep.getSessoesByDataAndSituacaoAndSala(data, bool, sala);
    }

    public List<Sessao> getSessoesBySituacao(Boolean bool) {
        return rep.getSessoesBySituacao(bool);
    }

    public Sessao insert(Sessao sessao) {
        Assert.isNull(sessao.getId(), "Erro ao gravar a sessao");
        return rep.save(sessao);
    }

    public Sessao disable(Sessao sessao) {
        sessao.setEncerrada(true);
        return rep.save(sessao);
    }

    public List<Sessao> getSessaoByFilme(Filme filme) {
        return rep.getSessoesByFilme(filme);
    }

    public List<Sessao> getSessaoBySala(Sala sala) {
        return rep.getSessoesBySala(sala);
    }

    public Sessao updateHora(Sessao sessao){
        Assert.notNull(sessao.getId(),"Não pode ser nulo");
        Optional<Sessao> sessaoOptional = rep.findById(sessao.getId());

        if(sessaoOptional.isPresent()){
            Sessao sessaoNova = sessaoOptional.get();
            sessaoNova.setHoraSessao(sessao.getHoraSessao());
            return rep.save(sessao);
        }
        return null;
    }


}
