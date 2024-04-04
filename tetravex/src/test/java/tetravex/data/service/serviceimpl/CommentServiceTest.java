package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import tetravex.TestSpringServer;
import tetravex.data.entity.Comment;
import tetravex.data.service.CommentService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {TestSpringServer.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class CommentServiceTest {

    private final String game = "game";
    private final String player = "player";
    private final String commentText = " Lorem Ipsum is de standaard proeftekst";
    //    @Autowired
    protected CommentService commentService;

    protected abstract void setTestedClass(CommentService commentService);


    @Test
    void addComment() {
        Date date = new Date();
        Comment comment = new Comment(game, player, commentText, date);

        commentService.reset();
        commentService.addComment(comment);
        List<Comment> commentList = commentService.getComments(game);
        Comment commentFromDB = commentList.get(0);

        assertCommentEquality(comment, commentFromDB);

        commentService.reset();
    }

    @Test
    void getComments() {
        Date date = new Date();
        Comment comment1 = new Comment(game, player + "1", commentText + "1", date);
        Comment comment2 = new Comment(game, player + "2", commentText + "2", date);
        Comment comment3 = new Comment(game, player + "3", commentText + "3", date);

        commentService.reset();
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);

        List<Comment> commentList = commentService.getComments(game);


        assertCommentEquality(comment1, commentList.get(0));
        assertCommentEquality(comment2, commentList.get(1));
        assertCommentEquality(comment3, commentList.get(2));

        commentService.reset();
    }

    @Test
    void reset() {
        Date date = new Date();

        Comment comment1 = new Comment(game, player + "1", commentText + "1", date);
        Comment comment2 = new Comment(game, player + "2", commentText + "2", date);
        Comment comment3 = new Comment(game, player + "3", commentText + "3", date);

        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);

        commentService.reset();
        List<Comment> commentList = commentService.getComments(game);

        assertTrue(commentList.isEmpty());
    }


    private void assertCommentEquality(Comment comment1, Comment comment2) {
        assertEquals(comment1.getGame(), comment2.getGame());
        assertEquals(comment1.getPlayer(), comment2.getPlayer());
        assertEquals(comment1.getCommentedOn(), comment2.getCommentedOn());
        assertEquals(comment1.getComment(), comment2.getComment());
    }

}