package com.anamariafelix.ms_ticket_manager.model;


import com.anamariafelix.ms_ticket_manager.client.model.Event;
import com.anamariafelix.ms_ticket_manager.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String ticketId;

    private String cpf;

    private String customerName;

    private String customerMail;

    private Event event;

    private String BRLTotalAmount;

    private String USDTotalAmount;

    private Status status;
}
