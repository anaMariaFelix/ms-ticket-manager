package com.anamariafelix.ms_ticket_manager.dto;


import com.anamariafelix.ms_ticket_manager.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private String id;

    @NotBlank(message = "The nome must be informed")
    @Size(min = 3, message = "The username must contain at least 3 letters")
    private String name;

    @NotBlank(message = "The cpf must be informed")
    @CPF(message = "provide a valid CPF")
    private String cpf;

    @NotBlank(message = "The email must be informed")
    @Email(message = "Invalid email format!", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank(message = "The password must be informed")
    @Size(min = 6, max = 6, message = "The password must contain at least 6 and a maximum of 6 digits")
    private String password;

    @NotNull(message = "The role must be informed")
    private Role role;
}
