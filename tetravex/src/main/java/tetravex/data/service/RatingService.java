package tetravex.data.service;


import tetravex.data.entity.Rating;
import tetravex.data.service.exceptions.RatingException;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
    void reset() throws RatingException;
}
