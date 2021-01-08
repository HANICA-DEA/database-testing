package nl.han.oose.persistence;

import nl.han.oose.dto.AccountDTO;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {

    private AccountDAO sut;

    @BeforeEach
    void setUp() {
        sut = new AccountDAO();
    }

    @AfterEach
    void tearDown() throws Exception {
        loadDatabaseFixture("fixtureAllTablesRemoved.sql");
    }

    private void loadDatabaseFixture(String filename) throws SQLException, SpotitubePersistenceException {
        RunScript.execute(new ConnectionFactory().getConnection(),
                new InputStreamReader(this.getClass().getResourceAsStream("/fixtures/" + filename)));
    }

    @Test
    void getAllAccountsRetrievesAllAccountsFromDB() throws Exception {
        loadDatabaseFixture("fixtureBaseData.sql");
        var actualAccounts = sut.getAllAccounts();
        assertTrue(actualAccounts.contains(new AccountDTO("one", "onepass")));
        assertTrue(actualAccounts.contains(new AccountDTO("two", "twopass")));
        assertEquals(2, actualAccounts.size());
    }

    @Test
    void accountIsDeleted() throws Exception {
        loadDatabaseFixture("fixtureBaseData.sql");
        var accountToDelete = new AccountDTO("one", "onepass");
        assertTrue(getAllAccounts().contains(accountToDelete));
        sut.deleteAccount(accountToDelete);
        assertFalse(getAllAccounts().contains(accountToDelete));
    }

    private List<AccountDTO> getAllAccounts() throws SQLException, SpotitubePersistenceException {
        var accounts = new ArrayList<AccountDTO>();
        try (
                PreparedStatement stmt = new ConnectionFactory().getConnection()
                        .prepareStatement("SELECT * FROM accounts")
        ) {
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                accounts.add(new AccountDTO(resultSet.getString("username")
                        , resultSet.getString("password")));
            }
            return accounts;
        }
    }

    @Test
    void exceptionThrownIfAccountToDeleteDoesNotExist() throws Exception {
        loadDatabaseFixture("fixtureBaseData.sql");
        var spotitubePersistenceException = assertThrows(SpotitubePersistenceException.class,
                () -> sut.deleteAccount(new AccountDTO("nobody", "nopass")));
        assertEquals("Account does not exist in database. Nothing deleted.",
                spotitubePersistenceException.getMessage());
    }

    @Test
    void accountIsUpdated() throws Exception {
        loadDatabaseFixture("fixtureBaseData.sql");
        var accountToUpdate = new AccountDTO("one", "onepassUpdated");
        sut.updateAccount(accountToUpdate);
        var actualAccount = getAllAccounts().stream()
                .filter(accountDTO -> accountDTO.getUsername().equals("one")).findFirst().get();
        assertEquals("onepassUpdated", actualAccount.getPassword());
    }

    @Test
    void exceptionThrownIfAccountToUpdateDoesNotExist() throws Exception {
        loadDatabaseFixture("fixtureBaseData.sql");
        var spotitubePersistenceException = assertThrows(SpotitubePersistenceException.class,
                () -> sut.updateAccount(new AccountDTO("nobody", "nopass")));
        assertEquals("Account does not exist in database. Nothing updated.",
                spotitubePersistenceException.getMessage());
    }

}