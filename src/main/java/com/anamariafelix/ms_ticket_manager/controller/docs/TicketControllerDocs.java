package com.anamariafelix.ms_ticket_manager.controller.docs;

import com.anamariafelix.ms_ticket_manager.controller.exception.ErrorMessage;
import com.anamariafelix.ms_ticket_manager.dto.TicketCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.TicketResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

public interface TicketControllerDocs {

    @Operation(summary = "Create a new ticket", description = "Resources for creating a new ticket." +
            "Request requires the use of a bearer token. Access restricted to role='ADMIN'",
            tags = {"Ticket"},
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))),

                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados invalidos",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401", description = "Recurso não permito ao perfil de CLIENT",
                            content = @Content(mediaType = " application/json;charset=UTF-8",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404", description = "Event not found in event service.",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "503", description = "Error communicating with event service.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    ResponseEntity<TicketResponseDTO> create(TicketCreateDTO ticketCreateDTO);
}
