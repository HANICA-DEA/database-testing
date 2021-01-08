package nl.han.oose.persistence;

import nl.han.oose.dto.TrackDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {

    private ConnectionFactory connectionFactory = new ConnectionFactory();

    public List<TrackDTO> getAllTracks() throws SpotitubePersistenceException {
        var resultList = new ArrayList<TrackDTO>();
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM track")
        ) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                resultList.add(new TrackDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpotitubePersistenceException(e);
        }
        return resultList;
    }

}