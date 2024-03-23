package tetravex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import tetravex.consoleui.ConsoleUI;
import tetravex.data.service.CommentService;
import tetravex.data.service.RatingService;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.jpa.CommentServiceJPA;
import tetravex.data.service.serviceimpl.jpa.RatingServiceJPA;
import tetravex.data.service.serviceimpl.jpa.ScoreServiceJPA;

@SpringBootApplication
public class SpringClient {

    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI) {
        return s -> consoleUI.mainMenu();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    @Primary
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    @Primary
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    @Primary
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
}