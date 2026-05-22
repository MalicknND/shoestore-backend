package com.malick.shoestore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.malick.shoestore.entities.AdminUser;
import com.malick.shoestore.enums.Role;
import com.malick.shoestore.repositories.AdminUserRepository;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    public CommandLineRunner createDefaultAdmin(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email}") String adminEmail,
            @Value("${app.admin.password}") String adminPassword
    ) {
        return args -> {
            if (adminUserRepository.count() > 0) {
                return;
            }

            AdminUser adminUser = AdminUser.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .build();

            adminUserRepository.save(adminUser);
        };
    }
}
