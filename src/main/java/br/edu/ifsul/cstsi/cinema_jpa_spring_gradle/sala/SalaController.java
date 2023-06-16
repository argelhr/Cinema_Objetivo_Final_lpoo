package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao.SessaoService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class SalaController {
    private static final Scanner teclado = new Scanner(System.in);
    private static SalaService salaService;
    private static SessaoService sessaoService;

    {
        System.out.println("Nenhum filme desativado no momento");
    }

    public SalaController(SalaService salaService, SessaoService sessaoService) {
        SalaController.salaService = salaService;
        SalaController.sessaoService = sessaoService;
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
                case 3 -> desativar();
                case 4 -> reativar();
                case 5 -> listarSalas();
                default -> System.out.println("Opção incorreta tente novamente!");
            }

        } while (opcao != 0);

        CinemaController.main(null);

    }

    public static void inserir() {

        long opcao;

        do {

            Sala sala = new Sala();
            System.out.println("===========CADASTRO DE SALA===========");

            System.out.println("Informe a capacidade da sala");
            sala.setCapacidade(teclado.nextInt());
            teclado.nextLine();
            sala.setStatus(true);

            do {
                System.out.println(sala);
                System.out.println("Confira os dados informados(1.Sim 2.Não");
                opcao = teclado.nextLong();
                teclado.nextLine();
                if (opcao < 1L || opcao > 2L) System.out.println("Opção invalida, tente novamente...\n");
            } while (opcao < 1L || opcao > 2L);

            if (opcao == 1) {
                if (salaService.insertSala(sala) != null)
                    System.out.println("Cadastro de sala efetuado com sucesso: " + sala);
                else
                    System.out.println("Cadastro não pode ser executado");
            } else
                System.out.println("Operação cancelada...");

            do {
                System.out.println("Deseja realizar novamente?(1.Sim 2.Não");
                opcao = teclado.nextInt();
                teclado.nextLine();

                if (opcao < 1 || opcao > 2) {
                    System.out.println("Opção invalida, tente novamente...\n");
                }
            } while (opcao < 1 || opcao > 2);
        }
        while (opcao != 2);


    }

    public static void atualizar() {
        long opcao;

        do {

            List<Sala> salaList = getSalas(true);

            if (salaList != null) {
                salaList.forEach(System.out::println);
                System.out.println("Qual o código da sala a ser editada?(zero para finalizar)");
                opcao = teclado.nextLong();
                teclado.nextLine();
                if (opcao != 0) {

                    Sala sala = null;

                    for (Sala s : salaList) {
                        if (s.getId() == opcao) {
                            sala = s;
                        }
                    }

                    if (sala != null) {

                        do {
                            System.out.println("Capacidade: " + sala.getCapacidade());
                            System.out.println("Deseja alterar a capacidade da sala?(1.Sim 2 .Não)");
                            opcao = teclado.nextLong();
                            teclado.nextLine();
                            if (opcao < 1 || opcao > 2) {
                                System.out.println("Opção incorreta, tente novamente");
                            }
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
                                if (opcao < 1 || opcao > 2) System.out.println("Opção incorreta, tente novamente");
                            } while (opcao < 1 || opcao > 2);
                            if (opcao == 1) {
                                if (salaService.update(sala) != null) {

                                    System.out.println("Sala Atualizada: " + sala);
//                                    System.out.println("Sessões desativadas com esta sala foram desativadas");
                                }
//                            System.out.println("Sessoes desativadas por tabela:");
//                            List<Sessao> sessaos = sessaoService.findBysala(sala);
                            } else {
                                System.out.println("Operação cancelada pelo usuario...");
                            }

                        }
                    } else {
                        System.out.println("Nenhuma sala com este codigo foi encontrada");
                    }
                    System.out.println("Deseja tentar novamente?(1.Sim 2.Não)");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                    if (opcao == 2L) opcao = 0L;
                }
            } else {
                System.out.println("Nenhuma sala ativa até o momento");
                System.out.println("Retornando ao menu anterior...");
                opcao = 0;

            }
        } while (opcao != 0);
    }

    public static void desativar() {

        long opcao = 0;
        do {

            List<Sala> salaList = getSalas(true);

            if (salaList != null) {

                salaList.forEach(System.out::println);

                System.out.println("Qual a sala a ser desativada?");
                opcao = teclado.nextLong();
                teclado.nextLine();

                Sala sala = null;

                for (Sala s : salaList) {
                    if (s.getId() == opcao) {
                        sala = s;
                        break;
                    }
                }
                if (sala != null) {
                    do {
                        System.out.println("Tem certeza que deseja desativar?(1.sim 2.não)");
                        System.out.println(sala);
                        opcao = teclado.nextInt();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2) System.out.println("Opção incorreta, tente novamentel");
                    } while (opcao < 1 || opcao > 2);
                    if (opcao == 1) {
                        if (salaService.desativar(sala) != null) {
                            System.out.println("Sala desativada: " + sala);
                        } else
                            System.out.println("Ocorreu algum problema ao tentar desativar");
                    }
                } else {
                    System.out.println("Não foi encontrada nenhma sala com este codigo");
                }
                do {
                    System.out.println("Deseja tentar novamente?(1.Sim 2.Não)");
                    opcao = teclado.nextInt();
                    teclado.nextLine();
                    if (opcao < 1 || opcao > 2)
                        System.out.println("Opção invalida");
                } while ((opcao < 1 || opcao > 2));
                if (opcao == 2)
                    opcao = 0;

            } else {
                System.out.println("Não existem salas ativas no momento");
                opcao = 0;
            }
        } while (opcao != 0);
    }

    public static List<Sala> getSalas(Boolean bool) {
        List<Sala> salaList = salaService.getSalasBySituacao(bool);
        if (salaList.isEmpty()) return null;
        return salaList;
    }

    public static void reativar() {
        long opcao = 0;
        do {
            Sala sala = null;

            List<Sala> salas = salaService.getSalasBySituacao(false);

            if (!salas.isEmpty()) {
                salas.forEach(System.out::println);
                System.out.println("Qual o id da sala a ser desativada?(zero pra cancelar");
                opcao = teclado.nextLong();
                teclado.nextLine();

                if (opcao != 0) {
                    for (Sala s : salas) {
                        if (s.getId() == opcao) {
                            sala = s;
                            break;
                        }
                    }
                    do {
                        System.out.println(sala);
                        System.out.println("Deseja confirmar a reativação?(1.sim 2.Não)");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                        if (opcao < 1 || opcao > 2)
                            System.out.println("Opção incorreta, tente novamente");
                    }
                    while (opcao < 1 || opcao > 2);

                    if (opcao == 1) {
                        System.out.println("Sala reativada: " + salaService.reativar(sala));
                    }
                } else {
                    System.out.println("Operação cancelada");
                    do {
                        System.out.println("Deseja realizar o processo novamente?(1.Sim 2.Não");
                        opcao = teclado.nextLong();
                        teclado.nextLine();
                    }
                    while (opcao < 1 || opcao > 2);
                    if (opcao == 2)
                        opcao = 0;
                }

            } else {
                System.out.println("Nenhuma sala desativada no momento");
                opcao = 0;
            }

        }
        while (opcao != 0);
    }


    public static void listarSalas() {
        List<Sala> salaList = salaService.getSalas();
        if (salaList != null) {
            salaList.forEach(System.out::println);
        } else {
            System.out.println("Nenhuma sala cadastrada no momento");
        }
    }
}
