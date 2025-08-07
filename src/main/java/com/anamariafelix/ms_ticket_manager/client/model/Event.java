package com.anamariafelix.ms_ticket_manager.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private String id;
    private String eventName;
    private LocalDateTime dateTime;
    private String zipCode;

    private String street;
    private String neighborhood;
    private String city;
    private String uf;
}
