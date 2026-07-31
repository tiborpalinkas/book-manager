package com.example.bookmanager.service;

import com.example.bookmanager.entity.Role;
import com.example.bookmanager.repository.RoleRepository;
import com.example.bookmanager.repository.UserRepository;
import com.example.bookmanager.entity.User;
import com.example.bookmanager.model.RegistrationRequest;
import com.example.bookmanager.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(RegistrationRequest request) {
        User user = userRepository.findByUsername(request.username())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public void register(RegistrationRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalStateException("User already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found."));

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(role))
                .build();
        userRepository.save(user);
    }
}
