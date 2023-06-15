package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngressoService {
    @Autowired
    private IngressoRepository rep;
    public Integer ingressosBySessao(Sessao sessao){

        return rep.getIngressosBySessao(sessao);

    }
}
