package tetravex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import tetravex.data.service.CommentService;
import tetravex.data.service.RatingService;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.jpa.CommentServiceJPA;
import tetravex.data.service.serviceimpl.jpa.RatingServiceJPA;
import tetravex.data.service.serviceimpl.jpa.ScoreServiceJPA;

@SpringBootApplication
@Configuration
@EnableAsync
@EnableCaching
@EntityScan("tetravex.data.entity")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "tetravex.client.*"), basePackages = {"tetravex.server"})
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
