package com.turing.books_api.config;

import com.turing.books_api.entity.Role;
import com.turing.books_api.entity.User;
import com.turing.books_api.entity.enums.RoleName;
import com.turing.books_api.repository.RoleRepository;
import com.turing.books_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// Crea el admin y el user iniciales al arrancar. Las credenciales vienen del .env, nunca del código.
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.init.admin.username}")
    private String adminUsername;
    @Value("${app.init.admin.email}")
    private String adminEmail;
    @Value("${app.init.admin.password}")
    private String adminPassword;

    @Value("${app.init.user.username}")
    private String userUsername;
    @Value("${app.init.user.email}")
    private String userEmail;
    @Value("${app.init.user.password}")
    private String userPassword;

    @Override
    @Transactional
    public void run(String... args) {
        createUserIfMissing(adminUsername, adminEmail, adminPassword, RoleName.ADMIN);
        createUserIfMissing(userUsername, userEmail, userPassword, RoleName.USER);
    }

    // Si el usuario ya existe no lo toca, así se puede reiniciar la app sin duplicar
    private void createUserIfMissing(String username, String email, String rawPassword, RoleName roleName) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("Falta el rol " + roleName + " en la BD"));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.getRoles().add(role);

        userRepository.save(user);
    }
}
