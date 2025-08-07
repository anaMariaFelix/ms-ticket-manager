package com.anamariafelix.ms_ticket_manager.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDTO {


    @JsonProperty("id")
    private String eventId;
    private String eventName;

    @JsonProperty("dateTime")
    private LocalDateTime eventDate;
    private String zipCode;

    private String street;
    private String neighborhood;
    private String city;
    private String uf;
}
