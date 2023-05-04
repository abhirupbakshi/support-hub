package org.example.persistence.exception;

public class InvalidCredentialException extends Exception {
    public InvalidCredentialException() {
        super();
    }

    public InvalidCredentialException(String message) {
        super(message);
    }

    public InvalidCredentialException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialException(Throwable cause) {
        super(cause);
    }

    protected InvalidCredentialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
