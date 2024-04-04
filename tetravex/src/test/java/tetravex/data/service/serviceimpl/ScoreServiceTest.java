package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import tetravex.TestSpringServer;
import tetravex.data.entity.Score;
import tetravex.data.service.ScoreService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = {TestSpringServer.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ScoreServiceTest {
    private final String game = "game";
    private final String player = "player";
    protected ScoreService scoreService = null;

    protected abstract void setTestedClass(ScoreService commentService);

    @Test
    void addScore() {
        Date date = new Date();
        Score score1 = new Score(game, player, 8, date);
        Score score2 = new Score(game, player, 9, date);
        Score score3 = new Score(game, player, 10, date);

        scoreService.reset();

        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);

        List<Score> scoreList = scoreService.getTopScores(game);

        assertScoreEquality(score1, scoreList.get(0));
        assertScoreEquality(score2, scoreList.get(1));
        assertScoreEquality(score3, scoreList.get(2));

        scoreService.reset();
    }

    @Test
    void getTopScores() {
        Date date = new Date();
        Score score1 = new Score(game, player, 8, date);
        Score score2 = new Score(game, player, 9, date);
        Score score3 = new Score(game, player, 10, date);

        scoreService.reset();

        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);
        List<Score> scoreList = scoreService.getTopScores(game);

        assertTrue(scoreList.get(0).getPoints() < scoreList.get(1).getPoints());
        assertTrue(scoreList.get(1).getPoints() < scoreList.get(2).getPoints());

        scoreService.reset();
    }

    @Test
    void reset() {
        Date date = new Date();
        Score score1 = new Score(game, player, 8, date);
        Score score2 = new Score(game, player, 9, date);
        Score score3 = new Score(game, player, 10, date);

        scoreService.reset();
        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);
        scoreService.reset();

        assertTrue(scoreService.getTopScores(game).isEmpty());
    }

    private void assertScoreEquality(Score score, Score score1) {
        assertEquals(score.getGame(), score1.getGame());
        assertEquals(score.getPlayer(), score1.getPlayer());
        assertEquals(score.getPlayedOn(), score1.getPlayedOn());
        assertEquals(score.getPoints(), score1.getPoints());
    }
}