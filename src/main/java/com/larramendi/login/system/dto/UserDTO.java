package com.larramendi.login.system.dto;

import com.larramendi.login.system.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @NotBlank (message = "")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @NotBlank (message = "")
    private String password;

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
    }
}
