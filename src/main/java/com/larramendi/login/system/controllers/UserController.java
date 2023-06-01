package com.larramendi.login.system.controllers;

import com.larramendi.login.system.controllers.exceptions.EmailAlreadyExistsException;
import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.entities.User;
import com.larramendi.login.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsersList = userService.getAllUsers();
        return new ResponseEntity<>(allUsersList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO savedUser = userService.getUserById(id);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User existentUser = userService.findUserByEmail(userDTO.getEmail());
        String msg;
        if (existentUser != null && existentUser.getEmail() != null && !existentUser.getEmail().isEmpty()) {
            throw new EmailAlreadyExistsException("Esse e-mail ja esta cadastrado!");
        }
        UserDTO newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        UserDTO updateUser = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        String msg = "User successfully deleted!";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
