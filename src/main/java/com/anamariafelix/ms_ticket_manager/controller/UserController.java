package com.anamariafelix.ms_ticket_manager.controller;

import com.anamariafelix.ms_ticket_manager.dto.UserCreateDTO;
import com.anamariafelix.ms_ticket_manager.dto.UserResponseDTO;
import com.anamariafelix.ms_ticket_manager.model.User;
import com.anamariafelix.ms_ticket_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.anamariafelix.ms_ticket_manager.mapper.UserMapper.toUser;
import static com.anamariafelix.ms_ticket_manager.mapper.UserMapper.toUserDTO;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        User user = userService.create(toUser(userCreateDTO));

        return ResponseEntity.status(201).body(toUserDTO(user));
    }
}
