package com.turing.books_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.turing.books_api.entity.Role;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/*esta entity esta basada en la tabla users, y para que sea mas legible y con mejores practicas
ocupare lombok que nos ayuda con anotaciones en lugar de escribir mas cosas a mano
*/


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    //llave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(nullable = false)
    private boolean enabled = true;

    //para que hibernate le ponga la fecha en automatico
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    //parecido al de crear pero aqui se actualiza
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Roles del usuario (ADMIN, USER).     * <p>
     * - @ManyToMany: un usuario puede tener varios roles y un rol pertenece a varios usuarios.
     * - @JoinTable: la relación se guarda en la tabla intermedia "user_roles",     * que solo contiene user_id y role_id. Por eso no se crea una entidad para ella.     * - Set en lugar de List: evita roles duplicados y es más eficiente     * (con List, Hibernate borra y reinserta toda la colección en cada cambio).     * - FetchType.EAGER: los roles se cargan junto con el usuario porque siempre     * se necesitan al autenticar y son muy pocos registros.     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),//la llave foranea hacia users
            inverseJoinColumns = @JoinColumn(name = "role_id")//aqui la llave hacia roles
    )
    private Set<Role> roles = new HashSet<>(); //NullPointerException
}