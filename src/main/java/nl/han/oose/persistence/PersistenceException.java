package nl.han.oose.persistence;

public class PersistenceException extends RuntimeException {

    public PersistenceException(String message, Exception e) {
        super(message, e);
    }
}
