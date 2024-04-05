package tetravex;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tetravex.data.service.CommentService;
import tetravex.data.service.RatingService;
import tetravex.data.service.ScoreService;
import tetravex.data.service.serviceimpl.jdbc.CommentServiceJDBC;
import tetravex.data.service.serviceimpl.jdbc.RatingServiceJDBC;
import tetravex.data.service.serviceimpl.jdbc.ScoreServiceJDBC;
import tetravex.data.service.serviceimpl.jpa.CommentServiceJPA;
import tetravex.data.service.serviceimpl.jpa.RatingServiceJPA;
import tetravex.data.service.serviceimpl.jpa.ScoreServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan("tetravex.data.entity")
@Profile("test")
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
//        pattern = {"tetravex.server.*", "tetravex.client.*" }))
public class TestSpringServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestSpringServer.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    @Qualifier("scoreJPA")
    protected ScoreService scoreServiceJPA() {
        return new ScoreServiceJPA();
    }

    @Bean
    @Qualifier("scoreJDBC")
    protected ScoreService scoreServiceJDBC() {
        return new ScoreServiceJDBC();
    }


    @Bean
    @Qualifier("commentJPA")
    protected CommentService commentServiceJPA() {
        return new CommentServiceJPA();
    }

    @Bean
    @Qualifier("commentJDBC")
    protected CommentService commentServiceJDBC() {
        return new CommentServiceJDBC();
    }


    @Bean
    @Qualifier("ratingJPA")
    protected RatingService ratingServiceJPA() {
        return new RatingServiceJPA();
    }

    @Bean
    @Qualifier("ratingJDBC")
    protected RatingService ratingServiceJDBC() {
        return new RatingServiceJDBC();
    }

}