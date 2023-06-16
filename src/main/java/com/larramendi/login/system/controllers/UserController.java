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

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    //Retorna as informacoes de um unico usuario
    @GetMapping("user/{id}")
    public String getUserById(Model model, @PathVariable Long id) {
        UserDTO savedUser = userService.getUserById(id);
        model.addAttribute("user", savedUser);
        return "user";
    }

    //Retorna a lista com todos os usuarios
    @GetMapping("users")
    public String message(Model model) {
        List<UserDTO> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        return "users";
    }

    //Criar novo usuario
    @PostMapping("/register/save")
    public String submitForm(@Valid @ModelAttribute ("user") UserDTO userDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "register";
        }

        try {
            UserDTO userToSave = userService.createUser(userDTO);
            return "redirect:/users";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("emailError", "Email already exists");
            model.addAttribute("user", userDTO);
            return "register";
        }
    }

    //Atualizar usuario existente
    @PostMapping("users/{id}/update")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") UserDTO userDTO,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "updateUser";
        }

        try {
            UserDTO userToSave = userService.updateUser(userDTO, id);
            return "redirect:/users";
        } catch (EmailAlreadyExistsException e) {
            model.addAttribute("emailError", "Email already exists");
            model.addAttribute("user", userDTO);
            return "updateUser";
            }
    }

    //Deletar usuario do banco de dados
    @GetMapping("users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
