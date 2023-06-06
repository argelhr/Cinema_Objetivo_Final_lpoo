package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class FilmeController {
    private static final Scanner teclado = new Scanner(System.in);
    private static FilmeService filmeService;
    public FilmeController(FilmeService filmeService) {
        FilmeController.filmeService = filmeService;
    }

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("========Area do gerenciamento de filmes========");
            System.out.println("Escolha a opção para gerenciar os filmes");
            System.out.println("""
                    1. Inserir Filme
                    2. Alterar Filme
                    3. Desativar filme
                    4. Reativar filme
                    5. Listar filmes
                    0. Voltar ao menu anterior...""");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> alterar();
                case 3 -> desativar();
                case 4 -> reativar();
                case 5 -> listarFilmes();
                default -> System.out.println("Opção incorreta tente novamente!");
            }

        } while (opcao != 0);

        CinemaController.main(null);

    }

    public static void listarFilmes() {
        List<Filme> filmes = filmeService.getFilmes();

        if (filmes.size() != 0) {
            filmes.forEach(f -> {
                System.out.println("cod: " + f.getId() + " - " + f + "Duração: " + f.escreveTempo());
            });
        } else

            System.out.println("Não existem filmes cadastrados no momento\n");
    }


    public static void inserir() {

        Filme filme = new Filme();
        int opcao;

        System.out.println("========Inserir Filme========");
        do {
            System.out.print("Informe o nome do filme: ");
            filme.setTitulo(teclado.nextLine());
            System.out.print("Informe a duração em minutos: ");
            filme.setDuracao(teclado.nextInt());
            teclado.nextLine();
            filme.setSituacao(true);

            do {//validação das informações de menu
                System.out.println("========Confira os dados informados");
                System.out.println(filme);
                System.out.println("As informações estão corretas?(1.Sim 2.Não");
                opcao = teclado.nextInt();
                teclado.nextLine();
            }
            while (opcao < 1 || opcao > 2);
            if (opcao == 1)
                System.out.println("Filme adicionado com sucesso:" + filmeService.insert(filme));
            else
                System.out.println("========Operação de cadastro cancelada========");

            do {
                System.out.print("\nDeseja cadastrar novamente?(1.Sim 2.Não):");
                opcao = teclado.nextInt();
                teclado.nextLine();
            }
            while (opcao < 1 || opcao > 2);

        }
        while (opcao != 2);
        System.out.println("======== Retornando ao mennu anterior ========\n");

    }

    public static void alterar() {
        long opcao;
        do {
            List<Filme> filmes = filmeService.getFilmesBySituacao(true);

            if (filmes.size() != 0) {
                filmes.forEach(f -> {
                    System.out.println("cod: " + f.getId() + " - " + f);
                });

                System.out.println("Qual o código do filme para ser alterado?(zero para finalizar");
                opcao = teclado.nextLong();
                teclado.nextLine();
            } else {
                System.out.println("Não há filmes ativos no momento");
                opcao = 0;
            }

            if (opcao != 0) {
                Filme filme = filmeService.getFilmeById(opcao);

                if (filme != null) {

                    do {
                        System.out.println("Titulo:" + filme.getTitulo());
                        System.out.println("Deseja alterar?(1.Sim 2.Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1L || opcao > 2)
                            System.out.println("Operação invalida, tente novamente...\n");
                    } while (opcao < 1L || opcao > 2);

                    if (opcao == 1L) {
                        System.out.println("Qual o novo titulo?");
                        filme.setTitulo(teclado.nextLine());
                    }

                    do {
                        System.out.println("Duração: " + filme.escreveTempo());
                        System.out.println("Deseja alterar a duração?(1.Sim 2.Não)");
                        opcao = teclado.nextLong();
                        if (opcao < 1L || opcao > 2)
                            System.out.println("Operação invalida, tente novamente...\n");
                    } while (opcao < 1L || opcao > 2);

                    if (opcao == 1L) {
                        System.out.println("Informe em minutos a duração do filme");
                        filme.setDuracao(teclado.nextInt());
                        teclado.nextLine();
                    }
                    do {
                        System.out.println("===================================");
                        System.out.println(filme);
                        System.out.println("Deseja confirmar a troca?(1.Sim 2.Não)");
                        opcao = teclado.nextLong();
                        if (opcao < 1L || opcao > 2L)
                            System.out.println("Operação invalida, tente novamente...\n");
                    }
                    while (opcao < 1L || opcao > 2L);
                    if (opcao == 1L) {
                        if (filmeService.alterar(filme)) {
                            System.out.println("Filme atualizado com sucesso");
                        } else
                            System.out.println("Ocorreu algum problema na atualização...");

                    }
                } else {
                    System.out.println("Filme não foi encontrado");
                }
                do {
                    System.out.println("Deseja tentar novamente?(1.Sim 2.Não");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                    if (opcao < 1L || opcao > 2L)
                        System.out.println("Operação invalida, tente novamente...\n");

                } while (opcao < 1L || opcao > 2L);
                if (opcao == 2L)
                    opcao = 0;
            }


        }
        while (opcao != 0);
    }

    public static void desativar() {

        long opcao;

        do {

            List<Filme> filmes = filmeService.getFilmesBySituacao(true);

            if (filmes.size() != 0) {
                filmes.forEach(f -> {
                    System.out.println("cod: " + f.getId() + " - " + f);
                });

                System.out.println("Qual o código do filme para ser desativado?(zero para finalizar");
                opcao = teclado.nextLong();
                teclado.nextLine();
            } else {
                System.out.println("Não há filmes ativos no momento");
                opcao = 0;
            }

            if (opcao != 0) {

                Filme filme = filmeService.getFilmeByIdAndSituacao(opcao, true);

                if (filme != null) {

                    do {
                        System.out.println("Tem certeza que deseja desativar: " + filme.getTitulo());
                        System.out.println("(1.Sim 2.Não");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1L || opcao > 2L)
                            System.out.println("Operação invalida, tente novamente...\n");
                    }
                    while (opcao < 1L || opcao > 2L);

                    if (opcao == 1L) {
                        if (filmeService.desativa(filme.getId())) {
                            System.out.println("Filme Desativado com sucesso");
                            System.out.println(filme);
                        } else
                            System.out.println("Ocorreu algum problema...");
                    } else
                        System.out.println("Operação cancelada");
                } else {
                    System.out.println("Código de filme não foi encontrado");
                }
            }
        } while (opcao != 0);

    }

    public static void reativar() {

        long opcao;

        do {

            List<Filme> filmes = filmeService.getFilmesBySituacao(false);

            if (filmes.size() != 0) {
                filmes.forEach(f -> {
                    System.out.println("cod: " + f.getId() + " - " + f);
                });

                System.out.println("Qual o código do filme para ser reativado?(zero para finalizar");
                opcao = teclado.nextLong();
                teclado.nextLine();
            } else {
                System.out.println("Não há filmes ativos no momento");
                opcao = 0;
            }

            if (opcao != 0) {

                Filme filme = filmeService.getFilmeByIdAndSituacao(opcao, false);

                if (filme != null) {

                    do {
                        System.out.println("Tem certeza que deseja reativar: " + filme.getTitulo());
                        System.out.println("(1.Sim 2.Não");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1L || opcao > 2L)
                            System.out.println("Operação invalida, tente novamente...\n");
                    }
                    while (opcao < 1L || opcao > 2L);

                    if (opcao == 1L) {
                        if (filmeService.reativa(filme.getId())) {
                            System.out.println("Filme reativado com sucesso");
                            System.out.println(filme);
                        } else
                            System.out.println("Ocorreu algum problema...");
                    } else
                        System.out.println("Operação cancelada");
                    do {
                        System.out.println("Deseja reativar mais algum filme?(1.Sim 2.Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1L || opcao > 2L)
                            System.out.println("Operação invalida, tente novamente...\n");
                    } while (opcao < 1L || opcao > 2L);
                } else {
                    System.out.println("Código de filme não foi encontrado");
                }
            }
        } while (opcao != 0);

    }
}

//    public static void desativarFilme() {
//
//        long opcao;
////        listarFilmes();
//        do {
//            System.out.println("Qual o código do filme para excluir?(Zero para retornar ao menu anterior...");
//            opcao = teclado.nextLong();
//
//            if (opcao != 0) {
////                Filme filme = FilmeDAO.selectFilmeByID(opcao);
//                do {
//                    System.out.println("Tem certeza que deseja desativar este filme?(1.OK 2.Cancelar");
//                    opcao = teclado.nextLong();
//                } while (opcao < 1 || opcao > 2);
//                if (opcao == 1) {
////                    if (FilmeDAO.softdelete(filme.getCodfilme())) {
////                        System.out.println("Desativação do filme: " + filme.getTitulo() + ", concluida");
////                    } else System.out.println("Aconteceu algum problema");
////                }
//                do {
//                    System.out.println("Deseja realizar outra operação de delete?");
//                    opcao = teclado.nextLong();
//                    if (opcao < 1 || opcao > 2)
//                        System.out.println("Valor incorreto, tente novamente");
//                } while (opcao < 1 || opcao > 2);
//
//            }
//        }
//        while (opcao != 0);
//        FilmeController.main(null);
//
//
//    }
//
////    public static void reativarFilme() {
////        long opcao;
//        do {
////            List<Filme> filmes = FilmeDAO.buscarFilmesDesativados();
//
////            if (filmes.size() == 0) {
////                System.out.println("Não existem filmes desativados no momento");
////                FilmeController.main(null);
////            }
//
////            filmes.forEach(filme -> {
////                System.out.println("Cod: " + filme.getCodfilme() + "- " + filme.getTitulo());
//            });
//
//            opcao = teclado.nextLong();
//            teclado.nextLine();
//
//            System.out.println("Qual o código do filme para reativar?(Zero para retornar ao menu anterior...");
//
//            if (opcao != 0) {
////                Filme filme = FilmeDAO.selectFilmeByID(opcao);
//                do {
//                    System.out.println("Tem certeza que deseja reativar este filme?(1.OK 2.Cancelar");
//                    opcao = teclado.nextLong();
//                } while (opcao < 1 || opcao > 2);
//                if (opcao == 1) {
////                    if (FilmeDAO.updateFilmeSituacao(filme.getCodfilme()))
////                        System.out.println("Reativação do filme: " + filme.getTitulo() + ", concluida");
//                } else System.out.println("Aconteceu algum problema");
//            }
//        } while (opcao != 0);
//
//        FilmeController.main(null);
//
//    }
//
//}
