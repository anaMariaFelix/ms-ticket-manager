package com.anamariafelix.ms_ticket_manager.controller;

import com.anamariafelix.ms_ticket_manager.controller.docs.TicketControllerDocs;
import com.anamariafelix.ms_ticket_manager.dto.TicketBuyCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketResponseDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketUpdateDTO;
import com.anamariafelix.ms_ticket_manager.model.Ticket;
import com.anamariafelix.ms_ticket_manager.service.TicketService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.anamariafelix.ms_ticket_manager.mapper.TicketMapper.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ticket")
@Tag(name = "Ticket", description = "Endpoints for Managing Tickets")
public class TicketController implements TicketControllerDocs {

    private final TicketService ticketService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> create(@RequestBody @Valid TicketCreateDTO ticketCreateDTO){

        Ticket ticket = ticketService.create(ticketCreateDTO);

        return ResponseEntity.status(201).body(toTicketDTO(ticket));
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("/buy-ticket")
    public ResponseEntity<TicketResponseDTO> buyTicket(@RequestBody @Valid TicketBuyCreateDTO ticketBuyCreateDTO){

        Ticket ticket = ticketService.buyTicket(ticketBuyCreateDTO);

        return ResponseEntity.status(201).body(toTicketDTO(ticket));
    }

    @GetMapping("/get-ticket/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable String id) {
        Ticket ticket = ticketService.findById(id);
        return ResponseEntity.ok().body(toTicketDTO(ticket));
    }

    @PreAuthorize("hasAuthority('ADMIN') OR #cpf == principal.cpf")
    @GetMapping("/get-ticket-cpf/{cpf}")
    public ResponseEntity<Page<TicketResponseDTO>> findAllCpf(@Parameter(hidden = true) @PageableDefault(size = 5, sort = "eventName", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable String cpf) {
        Page<Ticket> tickets = ticketService.findAllCpf(pageable,cpf);

        Page<TicketResponseDTO> ticketResponseDTOPage = tickets.map(ticket -> toTicketDTO(ticket));

        return ResponseEntity.ok().body(ticketResponseDTOPage);
    }

    @GetMapping("/get-ticket-eventId/{eventId}")
    public ResponseEntity<List<TicketResponseDTO>> findAllEventId(@PathVariable String eventId) {
        List<Ticket> tickets = ticketService.findAllEventId(eventId);
        return ResponseEntity.ok().body(toListTicketDTO(tickets));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable String id, @RequestBody @Valid TicketUpdateDTO ticketUpdateDTO) {
        Ticket ticket = ticketService.update(id, ticketUpdateDTO);
        return ResponseEntity.ok().body(toTicketDTO(ticket));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete-ticket/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN') OR #cpf == principal.cpf")
    @DeleteMapping("/delete-ticket-by-ticketId-cpf")
    public ResponseEntity<Void> deleteByCpf(@RequestParam String cpf, @RequestParam String ticketId) {
        ticketService.deleteTicketByTicketIdCpf(cpf, ticketId);
        return ResponseEntity.noContent().build();
    }
}
