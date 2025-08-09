package com.anamariafelix.ms_ticket_manager.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {

    PENDING("pending"),
    COMPLETED("conpleted"),
    INACTIVE("inactive");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Status fromString(String value) {
        return Status.valueOf(value.toUpperCase());
    }
}
