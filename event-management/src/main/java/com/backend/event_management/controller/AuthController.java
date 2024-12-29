package com.backend.event_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.event_management.model.ReponseAuthentification;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.service.AuthenticationService;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<ReponseAuthentification> register(
            @RequestBody Utilisateur request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ReponseAuthentification> login(
            @RequestBody Utilisateur request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}