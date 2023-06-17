package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
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
                    1. Vender Ingressos
                    2. Cancelar Ingresso
                    3. Alterar Tipo de ingresso
                    4. Listar Ingressos de sessoes ativas
                    5. Listar Ingressos de sessoes finalizadas
                    0. Voltar ao menu anterior
                    Digite a opção desejada
                    """);
            opcao = teclado.nextInt();
            teclado.nextLine();

            switch (opcao) {
                case 1 -> vender();
                case 2 -> cancelar();
                case 3 -> alterar();
                case 4 -> listarIngressos(false);
                case 5 -> listarIngressos(true);
                case 0 -> CinemaController.main(null);
                default -> System.out.println("Opção inesperada " + opcao);
            }
        }
        while (opcao < 0 || opcao > 2);

    }

    public static void vender() {
        long opcao;

        List<Sessao> sessaoList = sessaoService.getSessoesBySituacao(false);
        sessaoList.forEach(System.out::println);
        if (sessaoList.isEmpty()) {
            System.out.println("Não há sessões ativas até o momento");
            return;
        }

        System.out.println("Qual a sessão que pretende vender ingresso?");
        opcao = teclado.nextLong();
        teclado.nextLine();

        if (opcao != 0) {
            Sessao sessao;
            sessao = null;

            for (Sessao s : sessaoList) {
                if (s.getId() == opcao) {
                    sessao = s;
                }
            }

            if (sessao != null) {
                int disp = sessao.buscaQtd() - ingressoService.ingressosBySessao(sessao);
                System.out.println("Quantidade disponivel:" + disp);
                if (disp > 0) {
                    int qtd;
                    do {
                        System.out.println("Qual a quantidade de ingresso?(zero para cancelar)");
                        qtd = teclado.nextInt();
                        teclado.nextLine();
                        if (qtd > disp) {
                            System.out.println("Quantidade solicitada não é possivel atender");
                        }
                    }
                    while (qtd > disp);

                    int x;
                    for (x = 0; x < qtd; x++) {

                        Ingresso ingresso = new Ingresso();

                        ingresso.setSessaoBySessao(sessao);

                        do {
                            System.out.printf("Qual tipo do ingresso n%2dº?(1.Meia 2.Inteira", (x + 1));
                            opcao = teclado.nextLong();
                            teclado.nextLine();
                            if (opcao < 0 || opcao > 2L)
                                System.out.println("ops, opção incorreta");
                        }
                        while (opcao < 0 || opcao > 2L);

                        if (opcao == 1L) {
                            ingresso.setTipo(Tipo_ingresso.MEIA);
                        } else if (opcao == 2L) {
                            ingresso.setTipo(Tipo_ingresso.INTEIRA);
                        } else {
                            System.out.println("Cancelando compra..");
                        }

                        do {
                            System.out.printf("Deseja confirmar a compra nº" + (x + 1));
                            System.out.println("\n(1.Sim 2.Não)");
                            opcao = teclado.nextLong();
                            teclado.nextLine();
                            if (opcao < 1 || opcao > 2)
                                System.out.println("Ops, opção incorreta, tente novamente");
                        }
                        while (opcao < 1 || opcao > 2);

                        ingresso = ingressoService.insert(ingresso);
                        System.out.println("Ingresso registrado com sucesso: " + ingresso);

                    }
                    System.out.printf("Processo de compra finalizado com %d ingressos vendidos\n", x);

                } else {
                    System.out.println("Sessão já esta lotada");
                }
            } else {
                System.out.println("Nenhuma sessao ativa no momento, venda de ingressos cancela no momento");
            }
        }
        else{
            System.out.println("Operador cancelou a solicitação...");
        }


    }

    public static void alterar() {

        long opcao;
        List<Ingresso> ingressos = ingressoService.findIngressos(false);
        if (ingressos.isEmpty()) {
            System.out.println("Não há ingressos cadastrados para sessoes ativas");
            return;
        }
        System.out.println("Qual o ingresso que deseja alterar o tipo?(zero para cancelar");
        opcao = teclado.nextLong();
        teclado.nextLine();

        if (opcao != 0) {
            Ingresso ingresso = null;
            for (Ingresso i : ingressos) {
                if (i.getId() == opcao) {
                    ingresso = i;
                }
            }
            if (ingresso != null) {
                if (ingresso.getTipo() == Tipo_ingresso.MEIA) {

                    do {
                        System.out.println("Deseja alterar o tipo de ingresso para o tipo Inteiro?(1.Sim 2.Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2)
                            System.out.println("Opção incorreta, tente novamente");
                    }
                    while (opcao < 1 || opcao > 2);

                } else {
                    do {
                        System.out.println("Deseja alterar o tipo de ingresso para o tipo Meio?(1.Sim 2.Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2)
                            System.out.println("Opção incorreta, tente novamente");
                    }
                    while (opcao < 1 || opcao > 2);
                }
                if (opcao == 1) {
                    ingresso = ingressoService.altera(ingresso);
                    System.out.println("Atualização de ingresso realizada com sucesso:" + ingresso);
                } else {
                    System.out.println("Operação cancelada no final....");
                }
            } else {
                System.out.println("Codigo de ingresso não foi encontrado ");
            }
        } else {
            System.out.println("operação cancelada no inicio...");
        }

    }

    public static void cancelar() {
        long opcao;
        List<Ingresso> ingressos = ingressoService.findIngressos(false);
        if (!ingressos.isEmpty()) {
            ingressos.forEach(System.out::println);
            System.out.println("Qual ingreso deseja cancelar?(zero para finalizar processo)");
            opcao = teclado.nextLong();
            teclado.nextLine();
            Ingresso ingresso = null;
            if (opcao != 0) {
                for (Ingresso i : ingressos) {
                    if (opcao == i.getId()) {
                        ingresso = i;
                        break;

                    }
                }
                if (ingresso != null) {

                    do {
                        System.out.println(ingresso);
                        System.out.println("Tem certeza que quer cancelar este ingresso?(1.Sim 2.Não");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1L || opcao > 2L)
                            System.out.println("Opção incorreta, tente novamente");
                    }
                    while (opcao < 1L || opcao > 2L);

                    if (opcao == 1L) {
                        if (ingressoService.delete(ingresso)) {
                            System.out.println("Ingresso excluido com sucesso");
                        } else {
                            System.out.println("Houve algum problema para excluir o ingresso");
                        }
                    } else {
                        System.out.println("Operação cancelada com sucesso");
                    }
                }
                System.out.println("Codigo de ingresso informado nao foi encontrado");
            } else
                System.out.println("Opção cancelada");
        } else System.out.println("Nennhum ingresso de sessao ativa disponivel para cancelar");
    }

    public static void listarIngressos(Boolean bool) {
        List<Ingresso> ingressos = ingressoService.findIngressos(bool);
        if (!ingressos.isEmpty()) {
            if (bool) {
                System.out.println("Ingressos de sessoes já finalizada: ");
                ingressos.forEach(System.out::println);
            } else {
                System.out.println("Ingressos de sessoes em aberto: ");
                ingressos.forEach(System.out::println);
            }
        } else {
            System.out.println("Nenhum ingresso de sessao " + (bool ? "encerrada" : "ativa") + "até o momento");
        }
    }
}
