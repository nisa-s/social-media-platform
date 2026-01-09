package com.socialmedia.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.backend.dto.AuthDTO.AuthResponse;
import com.socialmedia.backend.dto.AuthDTO.LoginRequest;
import com.socialmedia.backend.dto.AuthDTO.RegisterRequest;
import com.socialmedia.backend.dto.UserDTO;
import com.socialmedia.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        UserDTO created = userService.register(req.username(), req.email(), req.password());
        return new AuthResponse(created.getId(), created.getUsername());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        // UserService.login() username veya email kabul ediyor.
        UserDTO user = userService.login(req.email(), req.password());
        return new AuthResponse(user.getId(), user.getUsername());
    }
}
