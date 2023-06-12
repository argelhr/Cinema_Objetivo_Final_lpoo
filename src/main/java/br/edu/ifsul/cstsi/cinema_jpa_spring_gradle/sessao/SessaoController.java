package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sessao;

import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.CinemaController;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.Filme;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.filme.FilmeService;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.Sala;
import br.edu.ifsul.cstsi.cinema_jpa_spring_gradle.sala.SalaService;
import org.springframework.stereotype.Controller;

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
                    2. Alterar Sessao
                    3. Finalizar Sessao
                    4. Listar Sessões
                    0. Voltar ao menu anterior""");
            opcao = teclado.nextInt();
            teclado.nextLine();
            switch (opcao) {
                case 1 -> SessaoController.inserir();
                case 0 -> CinemaController.main(null);
            }
            System.out.println("Opção incorreta, tente novamente...");

        } while (opcao < 0 || opcao > 5);
    }

    public static void inserir() {
        long opcao;
        List<Filme> filmes = filmeService.getFilmesBySituacao(true);
        List<Sala> salas = salaService.getSalasBySituacao(true);


        do {
            Sessao sessao = new Sessao();

            if (!filmes.isEmpty()) {
                filmes.forEach(f -> System.out.println(f));
                System.out.println("Qual o filme para esta sessão?(Zero para cancelar)");
                opcao = teclado.nextLong();
                teclado.nextLine();
                sessao.setFilmeByCodFilme(filmeService.getFilmeByIdAndSituacao(opcao, true));
                if (sessao.getFilmeByCodFilme() != null) {
                    salas.forEach(sala -> System.out.println(sala));
                    System.out.println("Qual a sala dessa budega?(zero para cancelar");
                    opcao = teclado.nextLong();
                    teclado.nextLine();
                    sessao.setSalaByCodSala(salaService.getSalaByIdAndSituacao(opcao, true));
                    if (sessao.getSalaByCodSala() != null) {

                        int dia, mes, hora, min;
                        LocalDate data;
                        LocalTime horario;

                        do {
                            System.out.println("Qual o dia da sessão");
                            dia = teclado.nextInt();
                            teclado.nextLine();
                        }
                        while (dia < 0 || dia > 30);

                        do {
                            System.out.println("Qual o mes da sessao");
                            mes = teclado.nextInt();
                            teclado.nextLine();
                        }
                        while (mes < 1 || mes > 12);
                        do {
                            System.out.println("Informa o hora da sessao");
                            hora = teclado.nextInt();
                            teclado.nextLine();
                        }
                        while (hora < 0 || hora > 23);

                        do {
                            System.out.println("qual o minuto da sessao");
                            min = teclado.nextInt();
                            teclado.nextLine();
                        }
                        while (min < 0 || min > 59);

                        data = LocalDate.of(2023, mes, dia);
                        horario = LocalTime.of(min, hora);

                        do {
                            System.out.println("Qual o valor da entrada inteira");
                            sessao.setValorInteira(teclado.nextDouble());
                            teclado.nextLine();
                        } while (sessao.getValorInteira() <= 0);

                        do {
                            System.out.println("Qual o valor da entrada meia?");
                            sessao.setValorMeia(teclado.nextDouble());
                            teclado.nextLine();
                        }
                        while (sessao.getValorMeia() <= 0);

                        sessao.setDtSessao(data);
                        sessao.setHorSessao(horario);

//                        sessao.verificaDisponibilidade();//todo terminar este metodo de verificar disponibilidade

                        System.out.println("Sessao adicionada: " + sessaoService.insert(sessao));
                    } else {
                        if (opcao == 0)
                            System.out.println("Operação cancelada");
                        else
                            System.out.println("Houve algum problema ao inserrir a sessao");
                    }
                } else {
                    if (opcao == 0)
                        System.out.println("Operação cancelada");
                    else
                        System.out.println("Filme nao foi encontrado, cadastr abortado");
                }
            } else {
                System.out.println("Nenhum filme foi encontrado, operação cancelada");
            }

            do {
                System.out.println("Deseja realizar o processo novamente?(1.sim 2.Não)");
                opcao = teclado.nextLong();
                teclado.nextLine();
                if (opcao < 1 || opcao > 2)
                    System.out.println("Ops, opção errada, tente novamente");
            } while (opcao < 1 || opcao > 2);
            if (opcao == 2)
                opcao = 0;

        }
        while (opcao != 0);
    }
}


