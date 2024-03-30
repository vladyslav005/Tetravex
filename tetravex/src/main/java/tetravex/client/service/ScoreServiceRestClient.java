package tetravex.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import tetravex.data.entity.Score;
import tetravex.data.service.ScoreService;

import java.util.Arrays;
import java.util.List;


public class ScoreServiceRestClient implements ScoreService {

    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;
//    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url, score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String gameName) {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Score[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
