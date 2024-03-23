package tetravex.data.service.serviceimpl.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import tetravex.data.entity.Rating;
import tetravex.data.exceptions.RatingException;
import tetravex.data.service.RatingService;


@Repository
@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        Rating ratingFromDb = null;

        try {
            ratingFromDb = entityManager.createQuery(
                    "SELECT r FROM Rating r WHERE r.player = '?'".replace("?", rating.getPlayer()),
                    Rating.class).getSingleResult();
        } catch (Exception ignored) {}

        if (ratingFromDb != null) {
            ratingFromDb.setRating(rating.getRating());
            ratingFromDb.setRatedOn(rating.getRatedOn());
            entityManager.merge(ratingFromDb);
        } else
            entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return entityManager.createQuery(
            "select avg(r.rating) from Rating r", Integer.class).getSingleResult();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return 0;
    }

    @Override
    public void reset() throws RatingException {

    }
}
