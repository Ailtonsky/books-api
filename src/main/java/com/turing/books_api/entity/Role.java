package com.turing.books_api.entity;

import com.turing.books_api.entity.enums.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//entidad de la tabla roles
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    //llave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;//short porque la columna tiene smallserial

    //Aqui se ponen los enum para que sean datos validos    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private RoleName name;
}