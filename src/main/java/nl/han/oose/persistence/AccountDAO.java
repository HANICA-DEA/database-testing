package nl.han.oose.persistence;

import nl.han.oose.dto.AccountDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private ConnectionFactory connectionFactory = new ConnectionFactory();

    public List<AccountDTO> getAllAccounts() throws SpotitubePersistenceException {
        var resultList = new ArrayList<AccountDTO>();
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM accounts")
        ) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                resultList.add(new AccountDTO(
                        resultSet.getString("username"),
                        resultSet.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpotitubePersistenceException(e);
        }
        return resultList;
    }

    public void deleteAccount(AccountDTO accountDTO) throws SpotitubePersistenceException {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement("DELETE FROM accounts WHERE username=?")
        ) {
            stmt.setString(1, accountDTO.getUsername());
            stmt.execute();
            if (stmt.getUpdateCount() != 1) {
                throw new SpotitubePersistenceException("Account does not exist in database. Nothing deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpotitubePersistenceException(e);
        }
    }

    public void updateAccount(AccountDTO accountDTO) throws SpotitubePersistenceException {
        try (
                Connection con = connectionFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement("UPDATE accounts SET password=? WHERE username=?")
        ) {
            stmt.setString(1, accountDTO.getPassword());
            stmt.setString(2, accountDTO.getUsername());
            stmt.execute();
            if (stmt.getUpdateCount() != 1) {
                throw new SpotitubePersistenceException("Account does not exist in database. Nothing updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpotitubePersistenceException(e);
        }
    }


}
