package com.larramendi.login.system.controllers;

import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    @Autowired
    UserService userService;

    @GetMapping("register")
    public String userRegistrationPage(Model model) {
        UserDTO userDTO = new UserDTO(); //Vazio para armazenar os dados do form
        model.addAttribute("userToRegister", userDTO);

        return "register-form";
    }

    @PostMapping("register/save")
    public String submitForm(Model model,
                             @ModelAttribute ("userToRegister") UserDTO userDTO) {
        UserDTO userToSave = userService.createUser(userDTO);
        model.addAttribute("userToRegister", userToSave);
        return "register-success";
    }


}
