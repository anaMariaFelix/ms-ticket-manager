package com.anamariafelix.ms_ticket_manager.controller.docs;

import com.anamariafelix.ms_ticket_manager.controller.exception.ErrorMessage;
import com.anamariafelix.ms_ticket_manager.dto.UserCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserControllerDocs {

    @Operation(summary = "Create a new user", description = "Resources for creating a new user",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Email user already registered in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<UserResponseDTO> create(UserCreateDTO userCreateDTO);
}
