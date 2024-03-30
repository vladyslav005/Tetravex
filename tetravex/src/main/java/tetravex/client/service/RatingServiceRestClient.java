package tetravex.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import tetravex.data.entity.Rating;
import tetravex.data.exceptions.RatingException;
import tetravex.data.service.RatingService;

public class RatingServiceRestClient implements RatingService {

    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return restTemplate.getForObject(url + "/" + game, Integer.class);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return restTemplate.getForObject(url + "/" + game + "/" + player, Integer.class);
    }

    @Override
    public void reset() throws RatingException {

    }
}
