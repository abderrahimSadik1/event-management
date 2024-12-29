package com.backend.event_management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.event_management.repository.UtilisateurRepository;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepository auteurRepository;

    public UserDetailsImpl(UtilisateurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return auteurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email not found"));
    }

}
