package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.SessaoService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class IngressoController {

    private static final Scanner teclado = new Scanner(System.in);
    public static IngressoService ingressoService;
    public static SessaoService sessaoService;

    public IngressoController(IngressoService ingressoService, SessaoService sessaoService) {
        IngressoController.ingressoService = ingressoService;
        IngressoController.sessaoService = sessaoService;
    }

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("""
                    =================AREA DE INGRESSO=============
                    1. Vender Ingresso
                    2. Cancelar Ingresso
                    Digite a opção desejada
                    """);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1 -> vender();
                case 2 -> cancelar();
                default -> System.out.println("Unexpected value: " + opcao);
            }
        }
        while (opcao < 1 || opcao > 2);

    }

    public static void vender() {
        long opcao;

        List<Sessao> sessaoList = sessaoService.getSessoesBySituacao(false);
        sessaoList.forEach(System.out::println);

        System.out.println("Qual a sessão que pretende vender ingresso?");
        opcao = teclado.nextLong();
        teclado.nextLine();

        Sessao sessao;
        sessao = null;

        for (Sessao s : sessaoList) {
            if (s.getId() == opcao) {
                sessao = s;
            }
        }

        if (sessao != null) {
            Integer disp = sessao.buscaQtd() - ingressoService.ingressosBySessao(sessao);
            System.out.println("Quantidade disponivel:" + disp);
            if (disp > 0) {
                do {
                    System.out.println("Qual tipo de ingresso?(1.Meia 2.Inteira");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                    if(opcao<1L || opcao > 2L)
                        System.out.println("ops, opção incorreta");
                }
                while (opcao<1L || opcao>2L);
                if(opcao == 1){
                    Ingresso ingresso = new Ingresso();
                    ingresso.setTipo(Tipo_ingresso.MEIA);
                    System.out.println(ingresso);
                }

            }
        }


    }

    public static void cancelar() {
        System.out.println("a");
    }
}
