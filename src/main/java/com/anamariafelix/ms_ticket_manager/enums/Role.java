package com.anamariafelix.ms_ticket_manager.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {

    CLIENT,
    ADMIN;

    @JsonCreator
    public static Role fromString(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
