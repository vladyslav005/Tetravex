package tetravex.data.service.serviceimpl.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import tetravex.data.entity.Comment;
import tetravex.data.entity.Score;
import tetravex.data.exceptions.ScoreException;
import tetravex.data.service.ScoreService;

import java.util.List;


@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) throws ScoreException {
        entityManager.persist(score);
    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException {
        return entityManager.createQuery(
                "SELECT s FROM Score s WHERE s.game = '${game}' ORDER BY s.points".replace("${game}", game),
                Score.class
        ).setMaxResults(10).getResultList();
    }

    @Override
    public void reset() throws ScoreException {
        entityManager.createQuery("DELETE Score", Comment.class);
    }
}
