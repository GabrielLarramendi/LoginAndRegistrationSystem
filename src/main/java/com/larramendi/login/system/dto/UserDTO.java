package com.larramendi.login.system.dto;

import com.larramendi.login.system.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private Date birth;
    private String email;
    private String password;

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        birth = user.getBirth();
        email = user.getEmail();
        password = user.getPassword();
    }
}
