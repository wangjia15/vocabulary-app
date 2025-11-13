package com.example.vocabularyapp.controller;

import com.example.vocabularyapp.dto.AuthRequest;
import com.example.vocabularyapp.dto.AuthResponse;
import com.example.vocabularyapp.dto.RegisterRequest;
import com.example.vocabularyapp.entity.User;
import com.example.vocabularyapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request.getUsername(), request.getEmail(), request.getPassword());
        String token = userService.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername(), user.getNickname()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String token = userService.login(request.getUsername(), request.getPassword());
        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getUsername(), user.getNickname()));
    }
}