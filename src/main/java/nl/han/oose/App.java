package nl.han.oose;

import nl.han.oose.persistence.AccountDAO;
import nl.han.oose.persistence.SpotitubePersistenceException;
import nl.han.oose.persistence.TrackDAO;

public class App {

    public App() {

    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        try {
            new AccountDAO().getAllAccounts().forEach(System.out::println);
            new TrackDAO().getAllTracks().forEach(System.out::println);
        } catch (SpotitubePersistenceException e) {
            e.printStackTrace();
        }
    }
}
