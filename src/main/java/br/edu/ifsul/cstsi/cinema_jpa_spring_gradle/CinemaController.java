package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.FilmeController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.SalaController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.SessaoController;

import java.util.Scanner;

public class CinemaController {
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("Seja bem vindo ao CINEMAFLIX");
            System.out.println("Escolha a opção desejada");
            System.out.println("1. Gerenciar Filmes");
            System.out.println("2. Gerenciar Salas");
            System.out.println("3. Gerenciar Ingressos");
            System.out.println("4. Gerenciar Sessões");
            System.out.println("5. Vender ingressos");
            System.out.println("0. Finalizar o dia");
            opcao = teclado.nextInt();
            if (opcao < 0 || opcao > 5)
                System.out.println("Opção invalida!!");
            switch (opcao) {
                case 1 -> FilmeController.main(null);
                case 2 -> SalaController.main(null);
////            case 3 -> IngressoController(null);
                case 4 -> SessaoController.main(null);
                case 0 -> System.out.println("Dia Finalizado");
                default -> System.out.println("Opção invalida");
            }
        }
        while (opcao < 0 || opcao > 5);
    }

}
