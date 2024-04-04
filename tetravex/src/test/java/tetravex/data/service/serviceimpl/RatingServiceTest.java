package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import tetravex.TestSpringServer;
import tetravex.data.entity.Rating;
import tetravex.data.service.RatingService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TestSpringServer.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class RatingServiceTest {
    private final String game = "game";
    private final String player = "player";
    protected RatingService ratingService;

    protected abstract void setTestedClass(RatingService commentService);


    @Test
    void setRating() {
        Date date = new Date();
        Rating rating = new Rating(game, player, 5, date);

        ratingService.reset();
        ratingService.setRating(rating);
        int ratingFromDb = ratingService.getRating(game, player);
        assertEquals(rating.getRating(), ratingFromDb);

        rating.setRating(0);
        ratingService.setRating(rating);
        ratingFromDb = ratingService.getRating(game, player);
        assertEquals(rating.getRating(), ratingFromDb);

        ratingService.reset();
    }

    @Test
    void getAverageRating() {
        Date date = new Date();
        Rating rating1 = new Rating(game, player + "1", 5, date);
        Rating rating2 = new Rating(game, player + "2", 1, date);

        ratingService.reset();
        ratingService.setRating(rating1);
        ratingService.setRating(rating2);

        int expected = (rating1.getRating() + rating2.getRating()) / 2;
        assertEquals(expected, ratingService.getAverageRating(game));

        ratingService.reset();
    }

    @Test
    void getRating() {
        Date date = new Date();
        Rating rating = new Rating(game, player, 5, date);

        ratingService.reset();
        ratingService.setRating(rating);

        assertEquals(rating.getRating(), ratingService.getRating(game, player));

        ratingService.reset();
    }

    @Test
    void reset() {
        Date date = new Date();
        Rating rating1 = new Rating(game, player + "1", 5, date);
        Rating rating2 = new Rating(game, player + "1", 5, date);
        Rating rating3 = new Rating(game, player + "1", 5, date);

        ratingService.setRating(rating1);
        ratingService.setRating(rating2);
        ratingService.setRating(rating3);
        ratingService.reset();

        assertEquals(0, ratingService.getAverageRating(game));

    }
}