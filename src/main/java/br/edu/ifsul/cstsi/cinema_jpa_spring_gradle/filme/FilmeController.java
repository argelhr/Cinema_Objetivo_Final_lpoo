package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.Sessao;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.SessaoService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class FilmeController {
    private static final Scanner teclado = new Scanner(System.in);
    private static FilmeService filmeService;
    private static SessaoService sessaoService;

    public FilmeController(FilmeService filmeService, SessaoService sessaoService) {
        FilmeController.filmeService = filmeService;
        FilmeController.sessaoService = sessaoService;
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
                    5. Listar todos os filmes
                    6. Listar por titulo
                    0. Voltar ao menu anterior...""");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> alterar();
                case 3 -> desativar();
                case 4 -> reativar();
                case 5 -> listarFilmes();
                case 6 -> listarPorNome();
                case 0 -> System.out.println("Retornando ao menu inicial do cinema");
                default -> System.out.println("Opção incorreta tente novamente!");
            }

        } while (opcao > 0 && opcao <= 6);
        CinemaController.main(null);


    }

    public static void listarFilmes() {
        List<Filme> filmes = filmeService.getFilmes();

        if (!filmes.isEmpty())
            filmes.forEach(System.out::println);
        else
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

            do {
                System.out.println("========Confira os dados informados");
                System.out.println(filme);
                System.out.println("As informações estão corretas?(1.Sim 2.Não");
                opcao = teclado.nextInt();
                teclado.nextLine();
            }
            while (opcao < 1 || opcao > 2);
            if (opcao == 1)
                System.out.println("Filme adicionado com sucesso:\n" + filmeService.insert(filme));
            else
                System.out.println("========Operação de cadastro cancelada========");

            do {
                System.out.print("\nDeseja realziar o processo novamente?(1.Sim 2.Não):");
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
        Filme filme = null;
        do {
            List<Filme> filmes = filmeService.getFilmesBySituacao(true);

            if (!filmes.isEmpty()) {

                filmes.forEach(System.out::println);

                System.out.println("=============\nQual o código do filme para ser alterado?(zero para finalizar");
                opcao = teclado.nextLong();
                teclado.nextLine();


                for (Filme f : filmes) {
                    if (f.getId() == opcao) {
                        filme = f;
                        break;
                    }
                }

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
                            System.out.println("Filme atualizado com sucesso:\n" + filme);
                            List<Sessao> sessaoList = sessaoService.getSessaoByFilme(filme);
                            if (!sessaoList.isEmpty()) {
                                System.out.println("Sessões com este filme foram encerradas:");
                                for (Sessao s : sessaoList) {
                                    s = sessaoService.disable(s);
                                    System.out.println("Sessão de id " + s.getId() + "foi desativada por tabela");
                                }
                            }
                        } else {
                            System.out.println("Não foi possivel realizar o cancelamento");
                        }

                    } else {
                        System.out.println("Operação cancelada...");
                    }
                } else {
                    System.out.println("Filme não foi encontrado");
                }
            } else {
                System.out.println("Não há filmes ativos no momento");
            }
            do {
                System.out.println("Deseja tentar novamente?(1.Sim 2.Não");
                opcao = teclado.nextLong();
                teclado.nextLine();
                if (opcao < 1L || opcao > 2L)
                    System.out.println("Operação invalida, tente novamente...\n");

            } while (opcao < 1L || opcao > 2L);


        }
        while (opcao != 2L);
    }

    public static void desativar() {

        long opcao;

        do {

            List<Filme> filmes = filmeService.getFilmesBySituacao(true);

            if (!filmes.isEmpty()) {

                filmes.forEach(System.out::println);

                System.out.println("=========\nQual o código do filme para ser desativado?(zero para finalizar");
                opcao = teclado.nextLong();
                teclado.nextLine();

                if (opcao != 0) {

                    Filme filme = null;
                    for (Filme f : filmes) {
                        if (f.getId() == opcao) {
                            filme = f;
                            break;
                        }
                    }

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

                            if (filmeService.desativa(filme.getId()) != null) {
                                System.out.println("Filme Desativado com sucesso");
                                System.out.println(filme);
                            } else {
                                System.out.println("Ocorreu algum problema...");
                            }
                        } else {
                            System.out.println("Operação cancelada");
                        }
                    } else {
                        System.out.println("Código de filme não foi encontrado");
                    }
                }
            } else {
                System.out.println("Não há filmes ativos no momento");
                opcao = 0;
            }

        }
        while (opcao != 0);

    }

    public static void reativar() {

        long opcao;

        do {

            List<Filme> filmes = filmeService.getFilmesBySituacao(false);

            if (filmes.size() != 0) {
                filmes.forEach(System.out::println);

                System.out.println("Qual o código do filme para ser reativado?(zero para finalizar");
                opcao = teclado.nextLong();
                teclado.nextLine();


                if (opcao != 0) {

                    Filme filme = null;

                    for (Filme f : filmes) {
                        if (f.getId() == opcao) {
                            filme = f;
                            break;
                        }
                    }

                    if (filme != null) {

                        do {
                            System.out.println("Tem certeza que deseja reativar: " + filme.getTitulo());
                            System.out.println("?\n(1.Sim 2.Não");
                            opcao = teclado.nextLong();
                            teclado.nextLine();
                            if (opcao < 1L || opcao > 2L)
                                System.out.println("Operação invalida, tente novamente...\n");
                        }
                        while (opcao < 1L || opcao > 2L);

                        if (opcao == 1L) {
                            if (filmeService.reativa(filme.getId()) != null) {
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
            } else {
                System.out.println("Não há filmes desativados no momento");
                opcao = 0;
            }
        }
        while (opcao != 0);

    }

    public static void listarPorNome() {
        System.out.println("Qual o titulo que voce procura?");

        String nome = teclado.nextLine();

        List<Filme> filmes = filmeService.getFilmeByTitulo(nome);

        if (filmes != null) {
            System.out.println("Filmes encontrados");
            filmes.forEach(System.out::println);
        }
    }
}
