package com.anamariafelix.ms_ticket_manager.service.ticket;

import com.anamariafelix.ms_ticket_manager.client.EventClientOpenFeign;
import com.anamariafelix.ms_ticket_manager.client.dto.EventDTO;
import com.anamariafelix.ms_ticket_manager.client.model.Event;
import com.anamariafelix.ms_ticket_manager.dto.TicketBuyCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketUpdateDTO;
import com.anamariafelix.ms_ticket_manager.enums.Status;
import com.anamariafelix.ms_ticket_manager.exception.*;
import com.anamariafelix.ms_ticket_manager.model.Ticket;
import com.anamariafelix.ms_ticket_manager.model.User;
import com.anamariafelix.ms_ticket_manager.repository.TicketRepository;
import com.anamariafelix.ms_ticket_manager.service.TicketService;
import com.anamariafelix.ms_ticket_manager.service.UserService;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserService userService;

    @Mock
    private EventClientOpenFeign eventClientOpenFeign;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void create_ShouldSaveTicket_WhenEventExists() {
        TicketCreateDTO dto = new TicketCreateDTO();
        dto.setEventId("event123");
        dto.setBrlTotalAmount("100.00");
        dto.setUsdTotalAmount("20.00");

        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId("event123");
        eventDTO.setEventName("Show Teste");

        when(eventClientOpenFeign.findById("event123")).thenReturn(eventDTO);
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ticket savedTicket = ticketService.create(dto);


        assertNotNull(savedTicket);
        assertEquals("event123", savedTicket.getEvent().getId());
        assertEquals("100.00", savedTicket.getBRLTotalAmount());
        assertEquals("20.00", savedTicket.getUSDTotalAmount());
        assertEquals(Status.PENDING, savedTicket.getStatus());

        verify(eventClientOpenFeign).findById("event123");
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void create_ShouldThrowEventNotFoundException_WhenEventDoesNotExist() {
        TicketCreateDTO dto = new TicketCreateDTO();
        dto.setEventId("event404");
        dto.setBrlTotalAmount("100.00");
        dto.setUsdTotalAmount("20.00");

        when(eventClientOpenFeign.findById("event404"))
                .thenThrow(FeignException.NotFound.class);

        assertThrows(EventNotFoundException.class, () -> ticketService.create(dto));
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void create_ShouldThrowOpenFeignConectionException_WhenFeignErrorOccurs() {
        TicketCreateDTO dto = new TicketCreateDTO();
        dto.setEventId("eventError");
        dto.setBrlTotalAmount("100.00");
        dto.setUsdTotalAmount("20.00");

        when(eventClientOpenFeign.findById("eventError"))
                .thenThrow(FeignException.errorStatus(
                        "findById",
                        Response.builder()
                                .status(500)
                                .reason("Internal Server Error")
                                .request(Request.create(Request.HttpMethod.GET, "", Map.of(), null, null, null))
                                .build()
                ));

        OpenFeignConectionException ex = assertThrows(OpenFeignConectionException.class,
                () -> ticketService.create(dto));

        assertEquals("Error communicating with event service.", ex.getMessage());
        verify(ticketRepository, never()).save(any());
    }

    //Buy Ticket

    @Test
    void buyTicket_ShouldCompleteTicket_WhenTicketIsPending() {
        TicketBuyCreateDTO dto = new TicketBuyCreateDTO();
        dto.setTicketId("ticket123");
        dto.setCpf("12345678901");

        Ticket ticket = new Ticket();
        ticket.setTicketId("ticket123");
        ticket.setStatus(Status.PENDING);

        User user = new User();
        user.setCpf("12345678901");
        user.setName("João Silva");
        user.setEmail("joao@email.com");

        when(ticketRepository.findByTicketIdAndDeletedFalse("ticket123")).thenReturn(Optional.of(ticket));
        when(userService.findByCpf("12345678901")).thenReturn(user);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket result = ticketService.buyTicket(dto);

        assertNotNull(result);
        assertEquals(Status.COMPLETED, result.getStatus());
        assertEquals(user.getCpf(), result.getCpf());
        assertEquals(user.getName(), result.getCustomerName());
        assertEquals(user.getEmail(), result.getCustomerMail());

        verify(ticketRepository).save(result);
    }

    @Test
    void buyTicket_ShouldThrowException_WhenTicketAlreadyCompleted() {
        TicketBuyCreateDTO dto = new TicketBuyCreateDTO();
        dto.setTicketId("ticket123");
        dto.setCpf("12345678901");

        Ticket ticket = new Ticket();
        ticket.setTicketId("ticket123");
        ticket.setStatus(Status.COMPLETED);

        when(ticketRepository.findByTicketIdAndDeletedFalse("ticket123")).thenReturn(Optional.of(ticket));

        TicketUnavailableException exception = assertThrows(TicketUnavailableException.class, () -> {
            ticketService.buyTicket(dto);
        });

        assertEquals("Ticket unavailable! Already sold!", exception.getMessage());
        verify(ticketRepository, never()).save(any());
    }

    //findById
    @Test
    void findById_ShouldReturnTicket_WhenTicketExists() {
        String ticketId = "ticket123";
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setStatus(Status.PENDING);

        when(ticketRepository.findByTicketIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.findById(ticketId);

        assertNotNull(result);
        assertEquals(ticketId, result.getTicketId());
        assertEquals(Status.PENDING, result.getStatus());
    }

    @Test
    void findById_ShouldThrowTicketNotFoundException_WhenTicketDoesNotExist() {
        String ticketId = "ticket123";

        when(ticketRepository.findByTicketIdAndDeletedFalse(ticketId)).thenReturn(Optional.empty());

        TicketNotFoundException exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.findById(ticketId);
        });

        assertEquals("Ticket with id = ticket123 not found!", exception.getMessage());
    }

    //findAllCPF
    @Test
    void findAllCpf_ShouldReturnPageOfTickets() {
        String cpf = "12345678901";
        Pageable pageable = PageRequest.of(0, 10);

        Ticket ticket1 = new Ticket();
        ticket1.setTicketId("t1");
        ticket1.setCpf(cpf);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId("t2");
        ticket2.setCpf(cpf);

        Page<Ticket> page = new PageImpl<>(List.of(ticket1, ticket2), pageable, 2);

        when(ticketRepository.findByCpfAndDeletedFalse(pageable, cpf)).thenReturn(page);

        Page<Ticket> result = ticketService.findAllCpf(pageable, cpf);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertTrue(result.getContent().stream().allMatch(t -> t.getCpf().equals(cpf)));

        verify(ticketRepository).findByCpfAndDeletedFalse(pageable, cpf);
    }

    //findAllEventeId
    @Test
    void findAllEventId_ShouldReturnListOfTickets() {
        String eventId = "event123";

        Ticket ticket1 = new Ticket();
        ticket1.setTicketId("t1");

        ticket1.setEvent(new Event());
        ticket1.getEvent().setId(eventId);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId("t2");

        ticket2.setEvent(new Event());
        ticket2.getEvent().setId(eventId);

        List<Ticket> tickets = List.of(ticket1, ticket2);

        when(ticketRepository.findByEventIdAndDeletedFalse(eventId)).thenReturn(tickets);

        List<Ticket> result = ticketService.findAllEventId(eventId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream()
                .allMatch(t -> t.getEvent() != null && eventId.equals(t.getEvent().getId())));

        verify(ticketRepository).findByEventIdAndDeletedFalse(eventId);
    }

    //update
    @Test
    void update_ShouldUpdateTicket_WhenTicketNotSold() {
        String ticketId = "ticket123";

        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setCpf(null);

        TicketUpdateDTO dto = new TicketUpdateDTO();
        dto.setUsdTotalAmount("150.00");
        dto.setBrlTotalAmount("750.00");

        when(ticketRepository.findByTicketIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updated = ticketService.update(ticketId, dto);

        assertNotNull(updated);
        assertEquals(dto.getUsdTotalAmount(), updated.getUSDTotalAmount());
        assertEquals(dto.getBrlTotalAmount(), updated.getBRLTotalAmount());

        verify(ticketRepository).save(updated);
    }

    @Test
    void update_ShouldThrowException_WhenTicketSold() {
        // Arrange
        String ticketId = "ticket123";
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setCpf("12345678901"); // já vendido

        TicketUpdateDTO dto = new TicketUpdateDTO();
        dto.setUsdTotalAmount("150.00");
        dto.setBrlTotalAmount("750.00");

        // Mock findById para retornar o ticket
        when(ticketRepository.findByTicketIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(ticket));

        // Act & Assert
        UnableToUpdateTicketexception ex = assertThrows(UnableToUpdateTicketexception.class, () -> {
            ticketService.update(ticketId, dto);
        });

        assertEquals("Tickets that have been sold cannot be updated.", ex.getMessage());
        verify(ticketRepository, never()).save(any());
    }

    //delete
    @Test
    void deleteTicket_ShouldMarkTicketAsDeleted_WhenTicketExists() {
        String ticketId = "ticket123";

        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setDeleted(false);
        ticket.setStatus(Status.PENDING);

        when(ticketRepository.findByTicketIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ticketService.deleteTicket(ticketId);

        assertTrue(ticket.isDeleted());
        assertNotNull(ticket.getDeletedAt());
        assertEquals(Status.INACTIVE, ticket.getStatus());

        verify(ticketRepository).save(ticket);
    }

    @Test
    void deleteTicket_ShouldThrowTicketNotFoundException_WhenTicketDoesNotExist() {
        String ticketId = "ticket123";

        when(ticketRepository.findByTicketIdAndDeletedFalse(ticketId)).thenReturn(Optional.empty());

        TicketNotFoundException ex = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.deleteTicket(ticketId);
        });

        assertEquals("Ticket with id = ticket123 not found!", ex.getMessage());
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void deleteTicketByTicketIdCpf_ShouldMarkTicketAsDeleted_WhenTicketExists() {
        String cpf = "12345678901";
        String ticketId = "ticket123";

        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketId);
        ticket.setCpf(cpf);
        ticket.setDeleted(false);
        ticket.setStatus(Status.PENDING);

        when(ticketRepository.findByTicketIdAndCpfAndDeletedFalse(ticketId, cpf)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ticketService.deleteTicketByTicketIdCpf(cpf, ticketId);

        assertTrue(ticket.isDeleted());
        assertNotNull(ticket.getDeletedAt());
        assertEquals(Status.INACTIVE, ticket.getStatus());

        verify(ticketRepository).save(ticket);
    }

    @Test
    void deleteTicketByTicketIdCpf_ShouldThrowTicketNotFoundException_WhenTicketDoesNotExist() {
        String cpf = "12345678901";
        String ticketId = "ticket123";

        when(ticketRepository.findByTicketIdAndCpfAndDeletedFalse(ticketId, cpf)).thenReturn(Optional.empty());

        // Act & Assert
        TicketNotFoundException ex = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.deleteTicketByTicketIdCpf(cpf, ticketId);
        });

        assertEquals(String.format("Ticket with id = %s and cpf = %s not found!", cpf, ticketId), ex.getMessage());
        verify(ticketRepository, never()).save(any());
    }
}
