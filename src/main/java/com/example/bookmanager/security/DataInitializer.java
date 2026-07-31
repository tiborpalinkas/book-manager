package com.example.bookmanager.security;

import com.example.bookmanager.entity.Role;
import com.example.bookmanager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        List.of("ROLE_USER", "ROLE_ADMIN").forEach(this::ensureExists);
    }

    private void ensureExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = Role.builder()
                    .name(roleName)
                    .build();
            return roleRepository.save(role);
        });
    }
}
