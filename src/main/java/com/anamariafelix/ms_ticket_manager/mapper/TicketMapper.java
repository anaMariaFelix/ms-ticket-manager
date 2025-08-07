package com.anamariafelix.ms_ticket_manager.mapper;

import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketResponseDTO;
import com.anamariafelix.ms_ticket_manager.model.Ticket;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMapper {

    public static Ticket toTicket(TicketCreateDTO ticketCreateDTO){

        return new ModelMapper().map(ticketCreateDTO, Ticket.class);
    }

    public static TicketResponseDTO toTicketDTO(Ticket ticket){

        return new ModelMapper().map(ticket, TicketResponseDTO.class);
    }

}
