package tetravex.data.service.serviceimpl.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tetravex.data.entity.Comment;
import tetravex.data.service.CommentService;
import tetravex.data.exceptions.CommentException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CommentServiceJDBC implements CommentService {
    public static final String SELECT = "SELECT * FROM comment WHERE game = ? LIMIT 10";
    public static final String DELETE = "DELETE FROM comment";
    public static final String INSERT = "INSERT INTO comment (game, player, comment, commentedOn) VALUES (?, ?, ?, ?)";

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addComment(Comment comment) throws CommentException {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT)) {
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getPlayer());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem adding comment", e);
        }
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT)) {
            statement.setString(1, game);
            ResultSet resultSet = statement.executeQuery();
            List<Comment> commentList = new ArrayList<>();

            while (resultSet.next()) {
                Comment comment = new Comment(
                        resultSet.getString(2),
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getTimestamp(4)
                );

                commentList.add(comment);
            }

            return commentList;
        } catch (SQLException e) {
            throw new CommentException("Problem selecting comments", e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem deleting comment", e);
        }
    }
}