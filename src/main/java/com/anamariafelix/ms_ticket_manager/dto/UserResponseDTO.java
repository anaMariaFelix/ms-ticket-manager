package com.anamariafelix.ms_ticket_manager.dto;

import com.anamariafelix.ms_ticket_manager.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String id;
    private String name;
    private String cpf;
    private String email;
    private Role role;
}
