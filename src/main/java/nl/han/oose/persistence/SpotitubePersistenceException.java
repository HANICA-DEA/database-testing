package nl.han.oose.persistence;

public class SpotitubePersistenceException extends Exception {

    public SpotitubePersistenceException(Exception exception) {
        super(exception);
    }

    public SpotitubePersistenceException(String message) {
        super(message);
    }

    public SpotitubePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
