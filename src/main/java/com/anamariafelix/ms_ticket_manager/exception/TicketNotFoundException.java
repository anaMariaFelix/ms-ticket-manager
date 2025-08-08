package com.anamariafelix.ms_ticket_manager.exception;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String msg) {
        super(msg);
    }
}
