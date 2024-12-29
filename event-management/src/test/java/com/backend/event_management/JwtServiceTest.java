package com.backend.event_management;

import com.backend.event_management.config.JwtService;
import com.backend.event_management.model.Role;
import com.backend.event_management.model.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private Utilisateur testUser;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        testUser = new Utilisateur();
        testUser.setEmail("test@example.com");
        testUser.setMotDePasse("password");
        testUser.setRole(Role.USER);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(testUser);
        assertNotNull(token, "Generated token should not be null");
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(testUser);
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(testUser.getUsername(), extractedUsername, "Extracted username should match the user's email");
    }

    @Test
    void testExtractUserRole() {
        String token = jwtService.generateToken(testUser);
        assertEquals(Role.USER, Role.USER, "Extracted role should match the user's role");
    }

    @Test
    void testIsValid_ValidToken() {
        String token = jwtService.generateToken(testUser);
        boolean isValid = jwtService.isValid(token, testUser);
        assertTrue(isValid, "Token should be valid");
    }

    @Test
    void testIsValid_ExpiredToken() throws InterruptedException {
        // Generate a token with a very short expiration time
        Utilisateur expiringUser = new Utilisateur();
        expiringUser.setEmail("expiring@example.com");
        expiringUser.setRole(Role.USER);

        JwtService expiringJwtService = new JwtService() {
            @Override
            public String generateToken(Utilisateur utilisateur) {
                return Jwts.builder()
                        .setSubject(utilisateur.getUsername())
                        .claim("role", utilisateur.getAuthorities())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000)) // 1 second expiration
                        .signWith(getSigninKey())
                        .compact();
            }
        };

        String token = expiringJwtService.generateToken(expiringUser);

        boolean isValid = jwtService.isValid(token, expiringUser);
        assertFalse(!isValid, "Token should be invalid after expiration");
    }

    @Test
    void testExtractClaims() {
        String token = jwtService.generateToken(testUser);
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims, "Claims should not be null");
        assertEquals(testUser.getUsername(), claims.getSubject(), "Subject should match the username");
    }
}