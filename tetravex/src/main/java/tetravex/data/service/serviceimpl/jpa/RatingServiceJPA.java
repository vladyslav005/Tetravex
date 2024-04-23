package tetravex.data.service.serviceimpl.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import tetravex.data.entity.Rating;
import tetravex.data.exceptions.RatingException;
import tetravex.data.service.RatingService;


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
        } catch (Exception ignored) {
        }

        if (ratingFromDb != null) {
            ratingFromDb.setRating(rating.getRating());
            ratingFromDb.setRatedOn(rating.getRatedOn());
//            entityManager.merge(ratingFromDb);
        } else
            entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double rate = entityManager.createQuery(
                "select avg(r.rating) from Rating r WHERE game = '${game}'".replace("${game}", game),
                Double.class).getSingleResult();

        return rate == null ? 0 : (int) Math.round(rate);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Integer rating = 0;
        try {
            rating = entityManager.createQuery(
                    "select r.rating from Rating r WHERE game = '${game}' AND player = '${player}'"
                            .replace("${game}", game)
                            .replace("${player}", player),
                    Integer.class).getSingleResult();
        } catch (NoResultException ignored) {}

        return rating;
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE FROM Rating").executeUpdate();
    }
}
