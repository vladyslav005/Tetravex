package tetravex.server.webservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import tetravex.data.entity.Comment;
import tetravex.data.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Profile("dev")
public class CommentServiceRest {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getAllComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

}
