package nl.han.oose.persistence;

import nl.han.oose.dto.TrackDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TrackDAO {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private ConnectionFactory connectionFactory = new ConnectionFactory();

    public List<TrackDTO> getAllTracks() throws SpotitubePersistenceException {
        var resultList = new ArrayList<TrackDTO>();
        try (
                var con = connectionFactory.getConnection();
                var stmt = con.prepareStatement("SELECT * FROM track")
        ) {
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                resultList.add(new TrackDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new SpotitubePersistenceException(e);
        }
        return resultList;
    }

}
