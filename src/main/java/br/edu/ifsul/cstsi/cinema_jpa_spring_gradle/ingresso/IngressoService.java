package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class IngressoService {
    @Autowired
    private IngressoRepository rep;

    public Integer ingressosBySessao(Sessao sessao) {

        return rep.getIngressosBySessao(sessao);

    }

    public List<Ingresso> findIngressos(Boolean bool) {
        List<Ingresso> ingressos = rep.findAllBySessaoSituacao(bool);
        return ingressos;
    }

    public Ingresso insert(Ingresso ingresso) {
        Assert.isNull(ingresso.getId(), "o ID precisa ser nulo para ser inserido");
        return rep.save(ingresso);
    }

    public Ingresso altera(Ingresso ingresso) {
        Assert.notNull(ingresso.getId(), "Não foi possivel atualziar por o id ser nulo");

        ingresso.setTipo(
                ingresso.getTipo() == Tipo_ingresso.MEIA ?
                        Tipo_ingresso.INTEIRA :
                        Tipo_ingresso.MEIA
        );

        return rep.save(ingresso);
    }

    public Boolean delete(Ingresso ingresso) {

        Assert.notNull(ingresso.getId(), "Ingresso não pode ter codigo nulo");

        rep.deleteById(ingresso.getId());
        return true;
    }
}
