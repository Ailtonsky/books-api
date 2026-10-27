package com.turing.books_api.repository;

import com.turing.books_api.entity.Role;
import com.turing.books_api.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Short> {
    Optional<Role> findByName(RoleName name);
}
