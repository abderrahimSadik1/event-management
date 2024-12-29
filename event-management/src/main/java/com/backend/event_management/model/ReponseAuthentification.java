package com.backend.event_management.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReponseAuthentification {
    private String token;
    private String message;
    private Role role;

    public ReponseAuthentification(String token, String message, Role role) {
        this.token = token;
        this.message = message;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
