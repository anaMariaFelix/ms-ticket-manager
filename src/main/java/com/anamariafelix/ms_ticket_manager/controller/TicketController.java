package com.anamariafelix.ms_ticket_manager.controller;

import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketResponseDTO;
import com.anamariafelix.ms_ticket_manager.model.Ticket;
import com.anamariafelix.ms_ticket_manager.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.anamariafelix.ms_ticket_manager.mapper.TicketMapper.toTicketDTO;

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

    @GetMapping("/get-event/{id}")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable String id) {
        Ticket ticket = ticketService.fidById(id);
        return ResponseEntity.ok().body(toTicketDTO(ticket));
    }

    @GetMapping("/get-event-cpf/{cpf}")
    public ResponseEntity<TicketResponseDTO> findByCpf(@PathVariable String cpf) {
        Ticket ticket = ticketService.findByCpf(cpf);
        return ResponseEntity.ok().body(toTicketDTO(ticket));
    }
}
