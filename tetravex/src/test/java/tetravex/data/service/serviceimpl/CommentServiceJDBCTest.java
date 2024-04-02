package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tetravex.client.SpringClient;
import tetravex.data.DatabaseConnection;
import tetravex.data.entity.Comment;
import tetravex.data.service.CommentService;
import tetravex.data.service.serviceimpl.jdbc.CommentServiceJDBC;
import tetravex.server.GameStudioServer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CommentServiceJDBCTest {

    private static CommentServiceJDBC commentServiceJDBC;
    private final String game = "game";
    private final String player = "player";
    private final String commentText = " Lorem Ipsum is de standaard proeftekst";


    @BeforeAll
    static void prepareConnection() {
        Connection connection = DatabaseConnection.getConnection();
        commentServiceJDBC = new CommentServiceJDBC();
    }


    @Test
    void addComment() {
        Date date = new Date();
        Comment comment = new Comment(game, player,commentText, date);

        commentServiceJDBC.reset();
        commentServiceJDBC.addComment(comment);
        List<Comment> commentList = commentServiceJDBC.getComments(game);
        Comment commentFromDB = commentList.get(0);

        assertCommentEquality(comment, commentFromDB);

        commentServiceJDBC.reset();
    }

    @Test
    void getComments() {
        Date date = new Date();
        Comment comment1 = new Comment(game, player + "1",commentText + "1", date);
        Comment comment2 = new Comment(game, player + "2",commentText + "2", date);
        Comment comment3 = new Comment(game, player + "3",commentText + "3", date);

        commentServiceJDBC.reset();
        commentServiceJDBC.addComment(comment1);
        commentServiceJDBC.addComment(comment2);
        commentServiceJDBC.addComment(comment3);

        List<Comment> commentList = commentServiceJDBC.getComments(game);

        assertCommentEquality(comment1, commentList.get(0));
        assertCommentEquality(comment2, commentList.get(1));
        assertCommentEquality(comment3, commentList.get(2));

        commentServiceJDBC.reset();
    }

    @Test
    void reset() {
        Date date = new Date();

        Comment comment1 = new Comment(game, player + "1",commentText + "1", date);
        Comment comment2 = new Comment(game, player + "2",commentText + "2", date);
        Comment comment3 = new Comment(game, player + "3",commentText + "3", date);

        commentServiceJDBC.addComment(comment1);
        commentServiceJDBC.addComment(comment2);
        commentServiceJDBC.addComment(comment3);

        commentServiceJDBC.reset();
        List<Comment> commentList = commentServiceJDBC.getComments(game);

        assertTrue(commentList.isEmpty());
    }


    private void assertCommentEquality(Comment comment1, Comment comment2) {
        assertEquals(comment1.getGame(), comment2.getGame());
        assertEquals(comment1.getPlayer(), comment2.getPlayer());
        assertEquals(comment1.getCommentedOn(), comment2.getCommentedOn());
        assertEquals(comment1.getComment(), comment2.getComment());
    }

}