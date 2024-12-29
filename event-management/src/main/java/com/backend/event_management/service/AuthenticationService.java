package com.backend.event_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.event_management.config.JwtService;
import com.backend.event_management.model.ReponseAuthentification;
import com.backend.event_management.model.Role;
import com.backend.event_management.model.Utilisateur;
import com.backend.event_management.repository.UtilisateurRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UtilisateurRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public ReponseAuthentification register(Utilisateur request) {

        // check if user already exist. if exist then authenticate the user
        if (repository.findByEmail(request.getUsername()).isPresent()) {
            return new ReponseAuthentification(null, "User already exist", null);
        }

        Utilisateur user = new Utilisateur();
        user.setUsername(request.getUsername());
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setNomUtilisateur(request.getNomUtilisateur());
        user.setTelephone(request.getTelephone());
        user.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user = repository.save(user);

        return new ReponseAuthentification(jwtService.generateToken(user), "User registration was successful",
                user.getRole());

    }

    public ReponseAuthentification authenticate(Utilisateur request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        Utilisateur user = repository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new ReponseAuthentification(token, "User login was successful", user.getRole());

    }

}