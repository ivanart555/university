package com.ivanart555.university.exception;

public class QueryNotExecuteException extends DAOException {

    public QueryNotExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryNotExecuteException(String message) {
        super(message);
    }
}
