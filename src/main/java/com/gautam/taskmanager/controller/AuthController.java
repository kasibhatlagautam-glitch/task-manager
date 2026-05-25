package com.gautam.taskmanager.controller;

import com.gautam.taskmanager.model.User;
import com.gautam.taskmanager.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        return authService.login(user);
    }
}