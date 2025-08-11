package com.anamariafelix.ms_ticket_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateDTO {

    private String brlTotalAmount ;
    private String usdTotalAmount;
}
