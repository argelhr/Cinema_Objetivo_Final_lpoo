package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class SalaController {
    private static final Scanner teclado = new Scanner(System.in);
    private static SalaService salaService;

    public SalaController(SalaService salaService) {
        SalaController.salaService = salaService;
    }

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("========Area do gerenciamento de sala========");
            System.out.println("Escolha a opção para gerenciar as salas");
            System.out.println("""
                    1. Cadastrar Sala
                    2. Alterar Capacidade da Sala
                    3. Desativar Sala
                    4. Reativar Sala
                    5. Listar Sala
                    0. Voltar ao menu anterior...""");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
//                case 3 -> desativar();
//                case 4 -> reativar();
//                case 5 -> listarFilmes();
                default -> System.out.println("Opção incorreta tente novamente!");
            }

        } while (opcao != 0);

        CinemaController.main(null);

    }

    public static void inserir() {

        Sala sala = new Sala();
        int opcao;

        System.out.println("===========CADASTRO DE SALA===========");

//        System.out.println("Informe o identificador da sala");
//        sala.setNrSala(teclado.nextLine());
        System.out.println("Informe a capacidade da sala");
        sala.setCapacidade(teclado.nextInt());
        teclado.nextLine();
        sala.setStatus(true);

        do {
            System.out.println(sala);
            System.out.println("Confira os dados informados(1.Sim 2.Não");
            opcao = teclado.nextInt();
            teclado.nextLine();
            if (opcao < 1 || opcao > 2) System.out.println("Opção invalida, tente novamente...\n");
        } while (opcao < 1 || opcao > 2);

        if (opcao == 1) {
//            salaService.insertSala(sala);
            System.out.println("Cadastro de sala efetuado com sucesso: " + salaService.insertSala(sala));
        }
        do {
            System.out.println("Deseja tentar novamente?(1.Sim 2.Não");
            opcao = teclado.nextInt();
            teclado.nextLine();
            if (opcao < 1 || opcao > 2) System.out.println("Opção invalida, tente novamente...\n");
        } while (opcao < 1 || opcao > 2);


    }

    public static void atualizar() {
        long opcao;

        do {
            List<Sala> salaList = salaService.getSalas();
            salaList.forEach(sala -> System.out.println(sala));
            if (!salaList.isEmpty()) {
                System.out.println("Qual o código da sala a ser editada?(zero para finalizar)");
                opcao = teclado.nextLong();
                teclado.nextLine();

                Sala sala = salaService.getSalaByID(opcao);

                if (sala != null) {

                    do {
                        System.out.println("Capacidade: " + sala.getCapacidade());
                        System.out.println("Deseja alterar a capacidade da sala?(1.Sim 2 .Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2) System.out.println("Opção incorreta, tente novamente");
                    } while (opcao < 1 || opcao > 2);

                    if (opcao == 1) {
                        System.out.println("Informe a nova quantidade: ");
                        sala.setCapacidade(teclado.nextInt());
                        teclado.nextLine();
                        do {
                            System.out.println("Confirme as informações\n" + sala);
                            System.out.println("Deseja confirmar a alteração?(1.Sim 2.Não");
                            opcao = teclado.nextLong();
                            teclado.nextLine();
                            if(opcao <1 || opcao>2)
                                System.out.println("Opção incorreta, tente novamente");
                        }
                        while (opcao < 1 || opcao > 2);
//                        if(opcao == 1L)
//                            salaService.update

                    }
                } else {
                    System.out.println("Nenhuma sala com este codigo foi encontrada");
                }
                System.out.println("Deseja tentar novamente?(1.Sim 2.Não)");
                opcao = teclado.nextLong();
                teclado.nextLine();
                if (opcao == 2)
                    opcao = 0;
            } else {
                System.out.println("Nenhuma sala cadastrada até o momento");
                opcao = 0;

            }
        }
        while (opcao != 0);
    }
}
