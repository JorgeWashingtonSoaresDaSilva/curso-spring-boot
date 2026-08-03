package com.jwss.studio.springboot.curso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IdexController {

    @GetMapping("/login")
    public String login() {
        return "index"; // Vai buscar o arquivo login.html na pasta templates
    }
    @GetMapping("/tela")
    public String tela() {
        return "telaBase"; // Vai buscar o arquivo login.html na pasta templates
    }
}
