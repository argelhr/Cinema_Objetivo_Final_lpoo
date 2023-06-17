package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.FilmeController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.ingresso.IngressoController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.SalaController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.SessaoController;

import java.util.Scanner;

public class CinemaController {
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {

            System.out.println("""
                    ==============HOME============
                    Escolha a opção desejada
                    1. Gerenciar Filmes
                    2. Gerenciar Salas
                    3. Gerenciar Sessões
                    4. Gerenciar Ingressos
                    0. Finalizar o dia
                    """);
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> FilmeController.main(null);
                case 2 -> SalaController.main(null);
                case 3 -> SessaoController.main(null);
                case 4 -> IngressoController.main(null);
                case 0 -> System.out.println("Dia Finalizado");
                default -> System.out.println("Opção invalida");
            }
        } while (opcao > 0 && opcao <= 4);
    }
    // todo desativar sala -> desativar sessao
    // todo desativar filme -> desativa sessao
    // todo arrumar controller ingresso
    // todo verificar vender
}
