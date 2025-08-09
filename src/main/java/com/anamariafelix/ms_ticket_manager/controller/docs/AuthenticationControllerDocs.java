package com.anamariafelix.ms_ticket_manager.controller.docs;

import com.anamariafelix.ms_ticket_manager.controller.exception.ErrorMessage;
import com.anamariafelix.ms_ticket_manager.dto.UserLoginDTO;
import com.anamariafelix.ms_ticket_manager.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationControllerDocs {

    @Operation(summary = "Authenticate to the API", description = "Authentication features in the API",
            tags = {"authentication"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful and return of a Bearer token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<?> authentication(UserLoginDTO dto, HttpServletRequest request);
}
