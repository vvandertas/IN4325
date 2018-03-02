package com.vdtas;

/**
 * @author vvandertas
 */
public class SessionException extends Exception{
    public SessionException() {
    }

    public SessionException(String message) {
        super(message);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
