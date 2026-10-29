package com.turing.books_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//entidad basada en la tabla categories
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Column(nullable = false, unique = true, length = 60)
    private String slug;

    @Column(length = 255)
    private String description;


}
/*
CREATE TABLE categories (
    id           BIGSERIAL     PRIMARY KEY,    name         VARCHAR(60)   NOT NULL,    slug         VARCHAR(60)   NOT NULL,    description  VARCHAR(255),    CONSTRAINT uq_categories_name UNIQUE (name),    CONSTRAINT uq_categories_slug UNIQUE (slug));

 */