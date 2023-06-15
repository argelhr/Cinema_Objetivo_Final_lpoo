package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
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
                    5. Listar Salas
                    0. Voltar ao menu anterior...""");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
//                case 3 -> desativar();
//                case 4 -> reativar();
                case 5 -> listarSalas(true);
                default -> System.out.println("Opção incorreta tente novamente!");
            }

        } while (opcao != 0);

        CinemaController.main(null);

    }

    public static void inserir() {

        Sala sala = new Sala();
        int opcao;

        System.out.println("===========CADASTRO DE SALA===========");


        System.out.println("Informe a capacidade da sala");
        sala.setCapacidade(teclado.nextInt());
        teclado.nextLine();
        sala.setStatus(true);

        do {
            System.out.println(sala);
            System.out.println("Confira os dados informados(1.Sim 2.Não");
            opcao = teclado.nextInt();
            teclado.nextLine();
            if (opcao < 1 || opcao > 2)
                System.out.println("Opção invalida, tente novamente...\n");
        } while (opcao < 1 || opcao > 2);

        if (opcao == 1) {
            System.out.println("Cadastro de sala efetuado com sucesso: " + salaService.insertSala(sala));
        }
        do {
            System.out.println("Deseja tentar novamente?(1.Sim 2.Não");
            opcao = teclado.nextInt();
            teclado.nextLine();
            if (opcao < 1 || opcao > 2)
                System.out.println("Opção invalida, tente novamente...\n");
        } while (opcao < 1 || opcao > 2);


    }

    public static void atualizar() {
        long opcao;

        do {

            List<Sala> salaList = getSalas(true);

            if (!salaList.isEmpty()) {
                salaList.forEach(System.out::println);
                System.out.println("Qual o código da sala a ser editada?(zero para finalizar)");
                opcao = teclado.nextLong();
                teclado.nextLine();

                Sala sala = null;

                for(Sala s :salaList){
                    if(s.getId() == opcao)
                        sala = s;
                }

                if (sala != null) {

                    do {
                        System.out.println("Capacidade: " + sala.getCapacidade());
                        System.out.println("Deseja alterar a capacidade da sala?(1.Sim 2 .Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2)
                            System.out.println("Opção incorreta, tente novamente");
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
                System.out.println("Nenhuma sala ativa até o momento");
                System.out.println("Retornando ao menu anterior...");
                opcao = 0;

            }
        }
        while (opcao != 0);
    }
//    public static void desativar(){
//
//        Long opcao;
//
//        List<Sala> salaList = getSalas(true);
//
//        if(salaList!= null){
//
//            salaList.forEach(System.out::println);
//
//            System.out.println("Qual a sala a ser desativada?");
//            opcao = teclado.nextLong();
//            teclado.nextLine();
//
//            Sala sala = null;
//
//            for(Sala s : salaList){
//                if(Objects.equals(s.getId(), opcao)) {
//                    sala = s;
//                }
//                if(sala != null)
//            }
//        }
//    }
    public static List<Sala> getSalas(Boolean bool){
        List<Sala> salaList = salaService.getSalasBySituacao(bool);
        if(salaList.isEmpty())
            return null;
        return salaList;
    }
    public static void listarSalas(Boolean bool){
        List<Sala> salaList = getSalas(bool);
        if(salaList != null)
            salaList.forEach(System.out::println);
        else
            System.out.println("Nenhuma sala ativa no momento");
    }
}
