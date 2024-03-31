package tetravex.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import tetravex.data.entity.Comment;
import tetravex.data.exceptions.CommentException;
import tetravex.data.service.CommentService;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService {

    private final String url = "http://localhost:8080/api/comment";

    @Autowired
    private RestTemplate restTemplate;



    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url, comment, Comment.class);
    }


    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }


    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }

}
