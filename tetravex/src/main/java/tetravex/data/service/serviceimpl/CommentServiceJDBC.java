package tetravex.data.service.serviceimpl;

import tetravex.data.DatabaseConnection;
import tetravex.data.entity.Comment;
import tetravex.data.service.exceptions.CommentException;
import tetravex.data.service.CommentService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String SELECT = "SELECT * FROM comment WHERE game = ? LIMIT 10";
    public static final String DELETE = "DELETE FROM comment";
    public static final String INSERT = "INSERT INTO comment (game, player, comment, commentedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getPlayer());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4,  new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        }  catch (SQLException e) {
            throw new CommentException("Problem adding comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(SELECT)) {
            statement.setString(1, game);
            ResultSet resultSet = statement.executeQuery();
            List<Comment> commentList = new ArrayList<>();

            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getTimestamp(4)
                );

                commentList.add(comment);
            }

            return commentList;

        }  catch (SQLException e) {
            throw new CommentException("Problem selecting comments", e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try (Statement statement = DatabaseConnection.getConnection().createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem deleting comment", e);
        }
    }
}
