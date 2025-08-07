package com.anamariafelix.ms_ticket_manager.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBuyCreateDTO {

    private String ticketId;

    @NotBlank(message = "The cpf must be informed")
    private String cpf;

    @NotBlank(message = "The customerName must be informed")
    private String customerName;

    @NotBlank(message = "The customerMail must be informed")
    private String customerMail;
}
