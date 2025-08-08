package com.anamariafelix.ms_ticket_manager.dto;

import com.anamariafelix.ms_ticket_manager.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateDTO {

    private String brlTotalAmount ;
    private String usdTotalAmount;

    @NotNull(message = "The USDTotalAmount must be informed")
    private Status status;
}
