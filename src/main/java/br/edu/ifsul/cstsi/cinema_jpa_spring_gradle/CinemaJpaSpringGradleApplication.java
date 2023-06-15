package br.edu.ifsul.cstsi.cinema_jpa_spring_gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaJpaSpringGradleApplication {

    public static void main(String[] args) {

        SpringApplication.run(CinemaJpaSpringGradleApplication.class, args);
        System.out.println("Seja bem vindo ao CINEMAFLIX");
        CinemaController.main(null);
    }

}
