package com.accountsservices.Exceptions;

public class StatutNotActiveException extends RuntimeException  {
    public StatutNotActiveException(String message) {
        super(message);
    }
}
