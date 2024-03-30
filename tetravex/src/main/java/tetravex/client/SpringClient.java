package tetravex.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import tetravex.client.service.CommentServiceRestClient;
import tetravex.client.service.RatingServiceRestClient;
import tetravex.client.service.ScoreServiceRestClient;
import tetravex.consoleui.ConsoleUI;
import tetravex.data.service.CommentService;
import tetravex.data.service.RatingService;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.jpa.CommentServiceJPA;
import tetravex.data.service.serviceimpl.jpa.RatingServiceJPA;
import tetravex.data.service.serviceimpl.jpa.ScoreServiceJPA;


@SpringBootApplication
@Configuration
@EntityScan("tetravex.data.entity")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "tetravex.server.*"))
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
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
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public ScoreService scoreService() {return new ScoreServiceRestClient();}

    @Bean
    @Primary
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

    @Bean
    @Primary
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }
}