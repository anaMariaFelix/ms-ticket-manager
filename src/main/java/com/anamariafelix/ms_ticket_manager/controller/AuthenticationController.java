package com.anamariafelix.ms_ticket_manager.controller;

import com.anamariafelix.ms_ticket_manager.controller.docs.AuthenticationControllerDocs;
import com.anamariafelix.ms_ticket_manager.controller.exception.ErrorMessage;
import com.anamariafelix.ms_ticket_manager.dto.UserLoginDTO;
import com.anamariafelix.ms_ticket_manager.jwt.JwtToken;
import com.anamariafelix.ms_ticket_manager.jwt.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoint for Managing Authentication")
public class AuthenticationController implements AuthenticationControllerDocs {

    private final JwtUserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authentication(@RequestBody @Valid UserLoginDTO dto, HttpServletRequest request){

        log.info("Login authentication process {}",dto.getEmail());

        try{
            log.info("Log 1");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            log.info("Log 2");
            authenticationManager.authenticate(authenticationToken);
            log.info("Log 3");
            JwtToken token = userDetailsService.getTokenAuthenticated(dto.getEmail());
            log.info("Log 4 role ");
            return ResponseEntity.ok(token);

        }catch(AuthenticationException e){
            log.error("Bad Credentials from userName '{}'",dto.getEmail());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,"Invalid credentials"));
    }
}
