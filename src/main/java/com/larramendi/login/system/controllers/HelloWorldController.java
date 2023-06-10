package com.larramendi.login.system.controllers;

import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HelloWorldController {

    @Autowired
    UserService userService;

    @GetMapping("users")
    public String message(Model model) {
        List<UserDTO> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "users";
    }

    @GetMapping("user/{id}")
    public String getUserById(Model model, @PathVariable Long id) {
        UserDTO savedUser = userService.getUserById(id);
        model.addAttribute("user", savedUser);
        return "user";
    }
}
