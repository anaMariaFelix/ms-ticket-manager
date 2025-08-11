package com.anamariafelix.ms_ticket_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateDTO {

    private String ticketId;

    @NotBlank(message = "The eventId must be informed")
    private String eventId;

    @NotBlank(message = "The BRLTotalAmount must be informed")
    private String brlTotalAmount ;

    @NotBlank(message = "The USDTotalAmount must be informed")
    private String usdTotalAmount;
}
