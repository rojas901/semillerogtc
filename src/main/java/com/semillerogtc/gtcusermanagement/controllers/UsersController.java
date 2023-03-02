package com.semillerogtc.gtcusermanagement.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsersController {
    @GetMapping("ping")
    public String ping() {
        return "Hola desde el controlador de usuarios";
    }
}
