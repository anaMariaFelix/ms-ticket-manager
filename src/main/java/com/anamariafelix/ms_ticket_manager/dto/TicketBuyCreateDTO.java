package com.anamariafelix.ms_ticket_manager.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBuyCreateDTO {

    @NotBlank(message = "The cpf must be informed")
    private String ticketId;

    @NotBlank(message = "The cpf must be informed")
    @CPF(message = "provide a valid CPF")
    private String cpf;

}
