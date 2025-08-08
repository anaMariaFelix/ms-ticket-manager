package com.anamariafelix.ms_ticket_manager.controller;

import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketResponseDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketUpdateDTO;
import com.anamariafelix.ms_ticket_manager.model.Ticket;
import com.anamariafelix.ms_ticket_manager.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.anamariafelix.ms_ticket_manager.mapper.TicketMapper.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> create(@RequestBody @Valid TicketCreateDTO ticketCreateDTO){

        Ticket ticket = ticketService.create(ticketCreateDTO);

        return ResponseEntity.status(201).body(toTicketDTO(ticket));
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable String id) {
        Ticket ticket = ticketService.fidById(id);
        return ResponseEntity.ok().body(toTicketDTO(ticket));
    }

    @GetMapping("/get-ticket-cpf/{cpf}")
    public ResponseEntity<List<TicketResponseDTO>> findAllCpf(@PathVariable String cpf) {
        List<Ticket> tickets = ticketService.findAllCpf(cpf);
        return ResponseEntity.ok().body(toListTicketDTO(tickets));
    }

    @GetMapping("/get-ticket-eventId/{eventId}")
    public ResponseEntity<List<TicketResponseDTO>> findAllEventId(@PathVariable String eventId) {
        List<Ticket> tickets = ticketService.findAllEventId(eventId);
        return ResponseEntity.ok().body(toListTicketDTO(tickets));
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable String id, @RequestBody @Valid TicketUpdateDTO ticketUpdateDTO) {
        Ticket ticket = ticketService.update(id, ticketUpdateDTO);
        return ResponseEntity.ok().body(toTicketDTO(ticket));
    }

    @DeleteMapping("/delete-ticket/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-ticket-cpf/{cpf}")
    public ResponseEntity<Void> deleteByCpf(@PathVariable String cpf) {
        ticketService.deleteTicketByCpf(cpf);
        return ResponseEntity.noContent().build();
    }
}
