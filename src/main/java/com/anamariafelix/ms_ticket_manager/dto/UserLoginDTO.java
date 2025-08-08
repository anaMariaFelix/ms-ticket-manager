package com.anamariafelix.ms_ticket_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "Provide an email!")
    @Email(message = "Invalid email format!", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank(message = "The password must be entered")
    @Size(min = 6, max = 6)
    private String password;
}
