package tetravex.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tetravex.data.entity.Comment;
import tetravex.data.exceptions.CommentException;
import tetravex.data.service.CommentService;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("dev")
public class CommentServiceRestClient implements CommentService {

    @Value("${api.comment}")
    private String url;

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
