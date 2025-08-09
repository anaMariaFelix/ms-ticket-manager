package com.anamariafelix.ms_ticket_manager.controller.docs;

import com.anamariafelix.ms_ticket_manager.controller.exception.ErrorMessage;
import com.anamariafelix.ms_ticket_manager.dto.TicketBuyCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

public interface TicketControllerDocs {

    @Operation(summary = "Create a new ticket", description = "Resources for creating a new ticket." +
            "Request requires the use of a bearer token. Access restricted to role='ADMIN'",
            tags = {"Ticket"},
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))),

                    @ApiResponse(responseCode = "422", description = "Appeal not processed due to lack of data or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401", description = "Feature not allowed for CLIENT profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404", description = "Event not found in event service.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "503", description = "Error communicating with event service.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    ResponseEntity<TicketResponseDTO> create(TicketCreateDTO ticketCreateDTO);


    @Operation(summary = "Buy a ticket", description = "Resources for buy a new ticket." +
            "Request requires the use of a bearer token. Access restricted to role='CLIENT'",
            tags = {"Ticket"},
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))),

                    @ApiResponse(responseCode = "422", description = "Appeal not processed due to lack of data or invalid data",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401", description = "Feature not allowed for ADMIN profile",
                            content = @Content(mediaType = " application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404", description = "Ticket not found.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "409", description = "Ticket unavailable! Already sold!",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

            })
    ResponseEntity<TicketResponseDTO> buyTicket(@RequestBody @Valid TicketBuyCreateDTO ticketBuyCreateDTO);


    @Operation(summary = "Find a Ticket", description = "Resources to find a Ticket by ID." +
            "Request requires the use of a bearer token. Access restricted to role='ADMIN'",
            tags = {"Ticket"},
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully located",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = TicketResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Ticket not found.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = TicketResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Feature not allowed for CLIENT profile",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<TicketResponseDTO> findById(@PathVariable String id);

    @Operation(summary = "Retrieve all tickets for a customer",
            description = "Request requires the use of a bearer token.",
            tags = {"Ticket"},
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully located",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = TicketResponseDTO.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "I do not allow the CLIENT to use the resource if the CPF provided is not their own CPF.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    ResponseEntity<List<TicketResponseDTO>> findAllCpf(@PathVariable String cpf);


    @Operation(summary = "Retrieve all tickets for a Event", description = "Resources for finding tickets by Event ID",
            tags = {"Ticket"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully located",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = TicketResponseDTO.class))
                    )
            })
    ResponseEntity<List<TicketResponseDTO>> findAllEventId(@PathVariable String eventId);
}
