package com.turing.books_api.repository;

import com.turing.books_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Búsqueda para el login y para Spring Security (UserDetailsService)
    Optional<User> findByUsername(String username);

    // Para no crear usuarios duplicados al iniciar la aplicación
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
