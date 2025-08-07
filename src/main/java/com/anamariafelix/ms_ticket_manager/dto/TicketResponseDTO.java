package com.anamariafelix.ms_ticket_manager.dto;

import com.anamariafelix.ms_ticket_manager.client.model.Event;
import com.anamariafelix.ms_ticket_manager.enums.Status;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {

    private String ticketId;

    private String cpf;

    private String customerName;

    private String customerMail;

    private Event event;

    private String BRLTotalAmount;

    private String USDTotalAmount;

    private Status status;
}
