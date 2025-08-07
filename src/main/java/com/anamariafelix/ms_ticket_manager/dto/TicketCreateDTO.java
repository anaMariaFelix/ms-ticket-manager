package com.anamariafelix.ms_ticket_manager.dto;


import com.anamariafelix.ms_ticket_manager.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "The USDTotalAmount must be informed")
    private Status status;
}
