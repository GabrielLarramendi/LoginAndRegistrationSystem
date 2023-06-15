package com.larramendi.login.system.controllers;

import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.exceptions.EmailAlreadyExistsException;
import com.larramendi.login.system.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("register")
    public String userRegistrationPage(Model model) {
        UserDTO userDTO = new UserDTO(); //Vazio para armazenar os dados do form
        model.addAttribute("userToRegister", userDTO);

        return "register-form";
    }

    @PostMapping("register/save")
    public String submitForm(@Valid @ModelAttribute ("userToRegister") UserDTO userDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userToRegister", userDTO);
            return "register-form";
        }

        try {
            UserDTO userToSave = userService.createUser(userDTO);
            return "redirect:/users";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("emailError", "Email already exists");
            model.addAttribute("userToRegister", userDTO);
            return "register-form";
        }
    }

    @GetMapping("users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
