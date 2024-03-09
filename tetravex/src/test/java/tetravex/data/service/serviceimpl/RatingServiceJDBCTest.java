package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tetravex.data.PropertyReader;
import tetravex.data.entity.Rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class RatingServiceJDBCTest {
    private static RatingServiceJDBC ratingServiceJDBC = null;
    private final String game = "game";
    private final String player = "player";

    @BeforeAll
    static void prepareConnection() {
        PropertyReader propertyReader = new PropertyReader("application_test.properties");
        try {
            Connection connection = DriverManager.getConnection(
                    propertyReader.getDbURL(), propertyReader.getDBUsername(), propertyReader.getDBPassword());
            ratingServiceJDBC = new RatingServiceJDBC(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Test", e);
        }
    }

    @Test
    void setRating() {
        Date date = new Date();
        Rating rating = new Rating(game, player,5, date);

        ratingServiceJDBC.reset();
        ratingServiceJDBC.setRating(rating);
        int ratingFromDb = ratingServiceJDBC.getRating(game, player);
        assertEquals(rating.getRating(), ratingFromDb);

        rating.setRating(0);
        ratingServiceJDBC.setRating(rating);
        ratingFromDb = ratingServiceJDBC.getRating(game, player);
        assertEquals(rating.getRating(), ratingFromDb);

        ratingServiceJDBC.reset();
    }

    @Test
    void getAverageRating() {
        Date date = new Date();
        Rating rating1 = new Rating(game, player + "1",5, date);
        Rating rating2 = new Rating(game, player + "2",0, date);

        ratingServiceJDBC.reset();
        ratingServiceJDBC.setRating(rating1);
        ratingServiceJDBC.setRating(rating2);

        int expected = (rating1.getRating() + rating2.getRating()) / 2;
        assertEquals(expected, ratingServiceJDBC.getAverageRating(game));

        ratingServiceJDBC.reset();
    }

    @Test
    void getRating() {
        Date date = new Date();
        Rating rating = new Rating(game, player,5, date);

        ratingServiceJDBC.reset();
        ratingServiceJDBC.setRating(rating);

        assertEquals(rating.getRating(), ratingServiceJDBC.getRating(game, player));

        ratingServiceJDBC.reset();
    }

    @Test
    void reset() {
        Date date = new Date();
        Rating rating1 = new Rating(game, player+"1",5, date);
        Rating rating2 = new Rating(game, player+"1",5, date);
        Rating rating3 = new Rating(game, player+"1",5, date);

        ratingServiceJDBC.setRating(rating1);
        ratingServiceJDBC.setRating(rating2);
        ratingServiceJDBC.setRating(rating3);
        ratingServiceJDBC.reset();

        assertEquals(0, ratingServiceJDBC.getAverageRating(game));

    }
}