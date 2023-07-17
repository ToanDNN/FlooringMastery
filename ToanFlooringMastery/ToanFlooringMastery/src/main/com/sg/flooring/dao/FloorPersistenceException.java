package main.com.sg.flooring.dao;

public class FloorPersistenceException extends Exception {
    public FloorPersistenceException(String msg) {
        super(msg);
    }

    public FloorPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
