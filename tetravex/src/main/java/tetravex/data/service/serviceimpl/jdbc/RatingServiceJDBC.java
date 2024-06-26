package tetravex.data.service.serviceimpl.jdbc;

import tetravex.data.DatabaseConnection;
import tetravex.data.entity.Rating;
import tetravex.data.exceptions.RatingException;
import tetravex.data.service.RatingService;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String SELECT = "SELECT * FROM rating WHERE game = ? AND player = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, rated_on) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rating SET rating = ?, rated_on = ? WHERE game = ? AND player = ?";
    public static final String AVERAGE = "SELECT avg(rating) FROM rating WHERE game = ?;";

    Connection connection = DatabaseConnection.getConnection();

    public RatingServiceJDBC(Connection connection) {
        this.connection = connection;
    }

    public RatingServiceJDBC() {
    }

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (PreparedStatement insertStatement = connection.prepareStatement(INSERT);
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE)
        ) {
            int ratingWrittenInDb = getRating(rating.getGame(), rating.getPlayer());
            if (ratingWrittenInDb != 0) {
                if (ratingWrittenInDb == rating.getRating()) return;

                updateStatement.setInt(1, rating.getRating());
                updateStatement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
                updateStatement.setString(3, rating.getGame());
                updateStatement.setString(4, rating.getPlayer());
                updateStatement.executeUpdate();
            } else {
                insertStatement.setString(1, rating.getGame());
                insertStatement.setString(2, rating.getPlayer());
                insertStatement.setInt(3, rating.getRating());
                insertStatement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RatingException("Problem adding rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (PreparedStatement statement = connection.prepareStatement(AVERAGE)) {
            statement.setString(1, game);
            ResultSet resultSet = statement.executeQuery();
            int avgRate = 0;
            if (resultSet.next())
                avgRate = resultSet.getInt(1);

            return avgRate;

        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
            statement.setString(1, game);
            statement.setString(2, player);
            ResultSet resultSet = statement.executeQuery();

            int rate = 0;
            if (resultSet.next())
                rate = resultSet.getInt(5);

            return rate;

        } catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
