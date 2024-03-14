package tetravex.data.service.serviceimpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tetravex.data.DatabaseConnection;
import tetravex.data.PropertyReader;
import tetravex.data.entity.Comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceJDBCTest {

    private static CommentServiceJDBC commentServiceJDBC = null;
    private final String game = "game";
    private final String player = "player";
    private final String commentText = " Lorem Ipsum is de standaard proeftekst";

    @BeforeAll
    static void prepareConnection() {
        Connection connection = DatabaseConnection.getConnection();
        commentServiceJDBC = new CommentServiceJDBC(connection);
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
        assertEquals(comment1.getCommentedOn().getDate(), comment2.getCommentedOn().getDate());
        assertEquals(comment1.getComment(), comment2.getComment());
    }

}