package com.anamariafelix.ms_ticket_manager.exception;

public class UniqueViolationException extends RuntimeException {

    public UniqueViolationException(String msg) {
        super(msg);
    }
}
