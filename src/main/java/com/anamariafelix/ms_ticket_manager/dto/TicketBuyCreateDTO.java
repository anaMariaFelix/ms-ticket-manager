package com.anamariafelix.ms_ticket_manager.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBuyCreateDTO {

    private String ticketId;

    @NotBlank(message = "The cpf must be informed")
    @CPF(message = "provide a valid CPF")
    private String cpf;

    @NotBlank(message = "The customerName must be informed")
    private String customerName;

    @NotBlank(message = "The customerMail must be informed")
    private String customerMail;
}
