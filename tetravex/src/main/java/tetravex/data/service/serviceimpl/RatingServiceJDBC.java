package tetravex.data.service.serviceimpl;

import tetravex.data.DatabaseConnection;
import tetravex.data.entity.Rating;
import tetravex.data.service.exceptions.RatingException;
import tetravex.data.service.RatingService;
import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String SELECT = "SELECT * FROM rating WHERE game = ? AND player = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String UPDATE  = "UPDATE rating SET rating = ?, ratedOn = ? WHERE game = ? AND player = ?";
    public static final String AVERAGE  = "SELECT avg(rating) FROM rating WHERE game = ?;";


    @Override
    public void setRating(Rating rating) throws RatingException {
        try (PreparedStatement insertStatement = DatabaseConnection.getConnection().prepareStatement(INSERT);
             PreparedStatement updateStatement = DatabaseConnection.getConnection().prepareStatement(UPDATE)
        ) {
            int ratingWrittenInDb = getRating(rating.getGame(), rating.getPlayer());
            if (ratingWrittenInDb != -1) {
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
        }  catch (SQLException e) {
            throw new RatingException("Problem adding rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(AVERAGE)) {
            statement.setString(1, game);
            ResultSet resultSet = statement.executeQuery();
            int avgRate = -1;
            if (resultSet.next())
                avgRate = resultSet.getInt(1);

            return avgRate;

        }  catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(SELECT)) {
            statement.setString(1, game);
            statement.setString(2, player);
            ResultSet resultSet = statement.executeQuery();

            int rate = -1;
            if (resultSet.next())
                rate = resultSet.getInt(3);

            return rate;

        }  catch (SQLException e) {
            throw new RatingException("Problem selecting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Statement statement = DatabaseConnection.getConnection().createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
