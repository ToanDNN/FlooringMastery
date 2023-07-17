package main.com.sg.flooring.service;

import main.com.sg.flooring.dao.FloorPersistenceException;

public class FloorValidationException extends FloorPersistenceException {
    public FloorValidationException(String msg) {
        super(msg);
    }

    public FloorValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
