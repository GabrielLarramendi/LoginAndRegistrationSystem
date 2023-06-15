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
        model.addAttribute("user", userDTO);

        return "register-form";
    }

    @PostMapping("register/save")
    public String submitForm(@Valid @ModelAttribute ("user") UserDTO userDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "register-form";
        }

        try {
            UserDTO userToSave = userService.createUser(userDTO);
            return "redirect:/users";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("emailError", "Email already exists");
            model.addAttribute("user", userDTO);
            return "register-form";
        }
    }

    @GetMapping("users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("users/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") UserDTO userDTO,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "edit-user";
        }

        try {
            UserDTO user = userService.updateUser(userDTO, id);
            return "redirect:/users";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("emailError", "Email already exists");
            model.addAttribute("user", userDTO);
            return "edit-user";
        }
    }

}
