package tetravex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
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
        pattern = "tetravex.client.*"))
@Profile("dev")
public class GameStudioServer {

    public static void main(String[] args) {

        SpringApplication.run(GameStudioServer.class, args);
    }

    @Bean
    protected ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    protected CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    protected RatingService ratingService() {
        return new RatingServiceJPA();
    }

}
