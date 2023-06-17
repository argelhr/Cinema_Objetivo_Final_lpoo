package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.Filme;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.FilmeService;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.SalaService;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@Controller
public class SessaoController {
    public static final Scanner teclado = new Scanner(System.in);
    public static SessaoService sessaoService;
    public static FilmeService filmeService;
    public static SalaService salaService;

    public SessaoController(SessaoService sessaoService, FilmeService filmeService, SalaService salaService) {
        SessaoController.sessaoService = sessaoService;
        SessaoController.filmeService = filmeService;
        SessaoController.salaService = salaService;
    }

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("""
                    ==========Area de SESSOES==========
                    1. Cadastrar Sessao
                    2. Finalizar Sessao
                    3. Alterar Data da sessão
                    4. Listar Sessões Ativas
                    5. Listar Sessões Desativadas
                    0. Voltar ao menu anterior
                    """);
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> desativar();
                case 3 -> alterarHora();
                case 4 -> listarBySituacao(false);
                case 5 -> listarBySituacao(true);
                case 0 -> CinemaController.main(null);
                default -> System.out.println("Opção incorreta, tente novamente");
            }

        } while (opcao > 0 && opcao < 6);

    }

    public static void inserir() {

        long opcao;
        List<Filme> filmes = filmeService.getFilmesBySituacao(true);
        List<Sala> salas = salaService.getSalasBySituacao(true);

        do {
            if (!filmes.isEmpty() || !salas.isEmpty()) {
                Sessao sessao = new Sessao();

                filmes.forEach(System.out::println);
                System.out.println("Qual o filme para esta sessão?(Zero para cancelar)");
                opcao = teclado.nextLong();
                teclado.nextLine();
                for (Filme f : filmes) {
                    if (opcao == f.getId()) {
                        sessao.setFilmeByCodFilme(f);
                        break;
                    }
                }
                if (sessao.getFilmeByCodFilme() != null) {
                    salas.forEach(System.out::println);
                    System.out.println("Qual a sala dessa sessao?(zero para cancelar");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                    for (Sala s : salas) {
                        if (s.getId() == opcao) {
                            sessao.setSalaByCodSala(s);
                            break;
                        }
                    }
                    if (sessao.getSalaByCodSala() != null) {

                        LocalDate data;
                        LocalTime horario;

                        data = montaData();

                        if (data != null) {

                            sessao.setDtSessao(data);

                            horario = montaHora();

                            if (horario != null) {

                                sessao.setHoraSessao(horario);

                                if (verificaDisponibilidade(sessao)) {

                                    do {
                                        System.out.println("Qual o valor da entrada inteira");
                                        sessao.setValorInteira(teclado.nextDouble());
                                        teclado.nextLine();
                                        if (sessao.getValorInteira() <= 0) System.out.println("Valor incorreto");
                                    } while (sessao.getValorInteira() <= 0);

                                    do {
                                        System.out.println("Qual o valor da entrada meia?");
                                        sessao.setValorMeia(teclado.nextDouble());
                                        teclado.nextLine();
                                    } while (sessao.getValorMeia() <= 0);

                                    sessao.setEncerrada(false);

                                    do {
                                        System.out.println(sessao);
                                        System.out.println("Deseja confirmar o cadastro?(1.Sim 2.Não");
                                        opcao = teclado.nextLong();
                                        teclado.nextLine();
                                        if (opcao < 1 || opcao > 2)
                                            System.out.println("Ops, opção incorreta, tente novamente");
                                    } while (opcao < 1 || opcao > 2);
                                    if (opcao == 1) {
                                        System.out.println("Sessao adicionada: " + sessaoService.insert(sessao));
                                    } else {
                                        System.out.println("Operação cancelada");
                                    }
                                } else {
                                    System.err.println("Horario indisponivel, cadastro cancelado");
                                }
                            } else {
                                System.err.println("Horario informado é incorreto, cadastro cancelado");
                            }
                        } else {
                            System.err.println("Data informada é de formato incorreto");
                        }

                    } else {
                        if (opcao == 0) {
                            System.err.println("Operação cancelada");
                        } else {
                            System.err.println("Codigo de sala nao foi encontrado, cadastro cancelado");
                        }
                    }
                } else {
                    if (opcao == 0) {
                        System.err.println("Operação cancelada");
                    } else System.err.println("Filme nao foi encontrado, cadastro cancelado");
                }

                do {
                    System.err.flush();
                    System.out.flush();
                    System.out.println("Deseja realizar o processo novamente?(1.sim 2.Não)");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                    if (opcao < 1 || opcao > 2) System.out.println("Ops, opção errada, tente novamente");
                } while (opcao < 1 || opcao > 2);
                if (opcao == 2) opcao = 0;
            } else {
                System.err.println("Nenhum filme ou sala foram encontrado, cadastro cancelado");
                System.out.println("Retornando ao menu anterior");
                opcao = 0;
            }

        } while (opcao != 0);

    }

    public static void alterarHora() {
        long opcao;
        List<Sessao> sessaoList = sessaoService.getSessoesBySituacao(false);
        if (!sessaoList.isEmpty()) {

            sessaoList.forEach(System.out::println);
            System.out.println("Qual a sessão a ser atualizada?(zero para cancelar)");
            opcao = teclado.nextLong();
            teclado.nextLine();

            if (opcao != 0) {
                Sessao sessao = null;
                for (Sessao s : sessaoList) {
                    if (s.getId() == opcao) {
                        sessao = s;
                        break;
                    }
                }
                if (sessao != null) {
                    System.out.println("Qual o novo horario?");

                    sessao.setHoraSessao(montaHora());

                    if (verificaDisponibilidade(sessao)) {
                        do {
                            System.out.println("Deseja realmente atualizar o horario desta sessao?(1.sim 2.Não)");
                            opcao = teclado.nextLong();
                            teclado.nextLine();
                            if (opcao < 1 || opcao > 2)
                                System.out.println("Ops, opção incorreta");
                        }
                        while (opcao < 1 || opcao > 2);
                        if (opcao == 1) {
                            sessao = sessaoService.updateHora(sessao);
                            System.out.println("Sessao atualizada:" + sessao);
                        } else {
                            System.out.println("Operação cancelada no final pelo usuario");
                        }
                    } else {
                        System.out.println("O horario selecionado nao esta disponivel");
                    }
                } else {
                    System.out.println("Codigo informado nao encontrado nas sessoes");
                }
            } else {
                System.out.println("Cancelado pelo usuario no inicio ");
            }
        } else {
            System.out.println("Não existem sessoes ativas no momento");
        }
        System.out.println("Retornando ao menu anterior...");
    }

    public static void desativar() {
        long opcao;

        List<Sessao> sessaoList = sessaoService.getSessoesBySituacao(false);

        if (!sessaoList.isEmpty()) {

            sessaoList.forEach(System.out::println);

            System.out.println("Qual a sessão a ser desabilitada");
            opcao = teclado.nextLong();
            teclado.nextLine();

            Sessao sessao = null;
            for (Sessao s : sessaoList) {
                if (s.getId() == opcao) {
                    sessao = s;
                }
            }

            if (sessao != null) {

                do {
                    System.out.println(sessao);
                    System.out.println("Deseja desativar a seção acima?(1.sim 2.Não");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                } while (opcao != 1 && opcao != 2);

                if (opcao == 1) {
                    sessao = sessaoService.disable(sessao);
                    System.out.println("Sessao desabilitada:" + sessao);
                } else {
                    System.out.println("Operação foi cancelada");
                }
            } else {
                System.out.println("Sessao nao foi encontrada");
            }


        }
    }

    public static LocalDate montaData() {
        try {
            int dia, mes;
            LocalDate data;

            do {
                System.out.println("Qual o dia da sessão");
                dia = teclado.nextInt();
                teclado.nextLine();
                if (dia < 0 || dia > 31) System.out.println("Ops, dia informado é invalido, tente novamente");
            } while (dia < 1 || dia > 31);

            do {
                System.out.println("Qual o mes da sessao");
                mes = teclado.nextInt();
                teclado.nextLine();
                if (mes < 1 || mes > 12) System.out.println("Ops, valor de mes invalido, tente novamente");
            } while (mes < 1 || mes > 12);


            data = LocalDate.of(2023, mes, dia);

            return data;
        } catch (Exception e) {
            System.out.println("Data invalida...");
            return null;
        }
    }

    public static LocalTime montaHora() {
        try {
            int hora, min;

            do {
                System.out.println("Informa o hora da sessao");
                hora = teclado.nextInt();
                teclado.nextLine();
                if (hora < 0 || hora > 23) {
                    System.out.println("Ops, data invalida, tente novamente");
                }
            } while (hora < 0 || hora > 23);

            do {
                System.out.println("Informa o minuto da sessao");
                min = teclado.nextInt();
                teclado.nextLine();
                if (min < 0 || min > 59) System.out.println("Ops, data invalida, tente novamente");
            } while (min < 0 || min > 59);

            return LocalTime.of(hora, min);
        } catch (Exception e) {
            System.out.println("Horario invalido...");
            return null;
        }
    }

    public static Boolean verificaDisponibilidade(Sessao sessao) {
        List<Sessao> sessaoList = sessaoService.getSessoesByDataAndSituacaoAndSala(sessao.getDtSessao(), false, sessao.getSalaByCodSala());

        for (Sessao s : sessaoList) {
            if (s.getId() != sessao.getId()) {
                if (sessao.getHoraSessao().isBefore(s.getHoraSessao())) {
                    if (calculaTempoEntreSessoes(sessao, s)) return false;
                } else {
                    if (calculaTempoEntreSessoes(s, sessao)) return false;

                }
            }

        }
        return true;//
    }

    private static Boolean calculaTempoEntreSessoes(Sessao sessao, Sessao s) {
        Duration tempo;
        tempo = Duration.between(sessao.getHoraSessao(), s.getHoraSessao());
        long horas = tempo.toHours();
        long mins = tempo.toMinutes();
        return sessao.getFilmeByCodFilme().getDuracao() > horas * 60 + mins + 20;
    }

    public static void listarBySituacao(Boolean bool) {

        List<Sessao> sessaoList = sessaoService.getSessoesBySituacao(bool);
        if (sessaoList.isEmpty())
            System.out.println("Não foi encontrada nenhuma sessão " + (bool ? "encerrada" : "ativa"));
        else
            sessaoList.forEach(System.out::println);

    }
}



