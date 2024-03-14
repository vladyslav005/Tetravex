package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tetravex.data.DatabaseConnection;
import tetravex.data.PropertyReader;
import tetravex.data.entity.Score;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreServiceJDBCTest {
    private static ScoreServiceJDBC scoreServiceJDBC = null;
    private final String game = "game";
    private final String player = "player";

    @BeforeAll
    static void prepareConnection() {
        Connection connection = DatabaseConnection.getConnection();
        scoreServiceJDBC = new ScoreServiceJDBC(connection);
    }

    @Test
    void addScore() {
        Date date = new Date();
        Score score1 = new Score(game, player,8, date);
        Score score2 = new Score(game, player,9, date);
        Score score3 = new Score(game, player,10, date);

        scoreServiceJDBC.reset();

        scoreServiceJDBC.addScore(score1);
        scoreServiceJDBC.addScore(score2);
        scoreServiceJDBC.addScore(score3);

        List<Score> scoreList = scoreServiceJDBC.getTopScores(game);

        assertScoreEquality(score1, scoreList.get(0));
        assertScoreEquality(score2, scoreList.get(1));
        assertScoreEquality(score3, scoreList.get(2));

        scoreServiceJDBC.reset();
    }

    @Test
    void getTopScores() {
        Date date = new Date();
        Score score1 = new Score(game, player,8, date);
        Score score2 = new Score(game, player,9, date);
        Score score3 = new Score(game, player,10, date);

        scoreServiceJDBC.reset();

        scoreServiceJDBC.addScore(score1);
        scoreServiceJDBC.addScore(score2);
        scoreServiceJDBC.addScore(score3);

        List<Score> scoreList = scoreServiceJDBC.getTopScores(game);

        assertTrue(scoreList.get(0).getPoints() < scoreList.get(1).getPoints());
        assertTrue(scoreList.get(1).getPoints() < scoreList.get(2).getPoints());

        scoreServiceJDBC.reset();
    }

    @Test
    void reset() {

        Date date = new Date();
        Score score1 = new Score(game, player,8, date);
        Score score2 = new Score(game, player,9, date);
        Score score3 = new Score(game, player,10, date);

        scoreServiceJDBC.reset();
        scoreServiceJDBC.addScore(score1);
        scoreServiceJDBC.addScore(score2);
        scoreServiceJDBC.addScore(score3);
        scoreServiceJDBC.reset();

        assertTrue(scoreServiceJDBC.getTopScores(game).isEmpty());
    }

    private void assertScoreEquality(Score score, Score score1) {
        assertEquals(score.getGame(), score1.getGame());
        assertEquals(score.getPlayer(), score1.getPlayer());
        assertEquals(score.getPlayedOn().getDate(), score1.getPlayedOn().getDate());
        assertEquals(score.getPoints(), score1.getPoints());
    }
}