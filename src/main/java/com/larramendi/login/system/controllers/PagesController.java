package com.larramendi.login.system.controllers;

import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PagesController {

    @Autowired
    UserService userService;

    //Retorna a pagina Index
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    /*Metodo que adiciona ao Model atributos que serao enviados para a view
      O objeto UserDTO estará disponível para ser acessado e exibido ou usado para preencher um formulário.
      O método retorna a string "register-form", que é o nome da view que será renderizada e exibida ao usuário.*/
    @GetMapping("/register")
    public String userRegistrationPage(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "register";
    }

    //Renderiza a pagina com os campos para alterar um unico usuario
    @GetMapping("users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }
}
