package com.anamariafelix.ms_ticket_manager.service;

import com.anamariafelix.ms_ticket_manager.client.EventClientOpenFeign;
import com.anamariafelix.ms_ticket_manager.client.dto.EventDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.exception.EventNotFoundException;
import com.anamariafelix.ms_ticket_manager.exception.OpenFeignConectionException;
import com.anamariafelix.ms_ticket_manager.exception.TicketNotFoundException;
import com.anamariafelix.ms_ticket_manager.model.Ticket;
import com.anamariafelix.ms_ticket_manager.repository.TicketRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.anamariafelix.ms_ticket_manager.client.mapper.EventMapper.toEvent;
import static com.anamariafelix.ms_ticket_manager.mapper.TicketMapper.toTicket;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventClientOpenFeign eventClientOpenFeign;

    @Transactional
    public Ticket create(TicketCreateDTO ticketCreateDTO) {
        try{
            EventDTO event = eventClientOpenFeign.findById(ticketCreateDTO.getEventId());

            Ticket ticket = toTicket(ticketCreateDTO);

            ticket.setEvent(toEvent(event));

            return ticketRepository.save(ticket);

        }catch (FeignException.NotFound e) {
            throw new EventNotFoundException("Event not found in event service.");

        } catch (FeignException e) {
            throw new OpenFeignConectionException("Error communicating with event service.");
        }
    }

    @Transactional(readOnly = true)
    public Ticket fidById(String id){
        return ticketRepository.findById(id).orElseThrow(
                () -> new TicketNotFoundException(String.format("Ticket with id = %s not found!", id)));
    }

    public Ticket findByCpf(String cpf) {
        return ticketRepository.findByCpf(cpf).orElseThrow(
                () -> new TicketNotFoundException(String.format("Ticket with user CPF = %s not found!", cpf)));
    }
}
