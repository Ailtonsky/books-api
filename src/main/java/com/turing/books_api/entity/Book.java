package com.turing.books_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    //Llave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 150)
    private String author;

    @Column(nullable = false, unique = true, length = 17)
    private String isbn;

    //TEXT porque no se sabe si es fijo lo que se va a escribir
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "publication_year")
    private Short publicationYear;


    @Column
    private Integer pages;

    /**
     * Precio del libro.     * Se usa BigDecimal y no double/float porque los tipos de punto flotante     * tienen errores de redondeo (0.1 + 0.2 != 0.3), inaceptables con dinero.     * precision = 10 dígitos en total, scale = 2 decimales -> NUMERIC(10, 2).     */    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /** URL o ruta de la portada (opcional). */
    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    /**
     * Categoría a la que pertenece el libro.     *     * - @ManyToOne: muchos libros pueden pertenecer a una misma categoría.     * - FetchType.LAZY: la categoría solo se consulta si realmente se usa.     *   (Por defecto @ManyToOne es EAGER, lo que puede generar consultas innecesarias.)     * - optional = false: todo libro debe tener categoría (category_id es NOT NULL).
     * - @JoinColumn indica la columna de llave foránea en la tabla books.
     */    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Fecha de creación, asignada automáticamente por Hibernate al insertar.     * Instant porque la columna es TIMESTAMPTZ (con zona horaria).     */    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /** Fecha de última modificación, actualizada automáticamente en cada UPDATE. */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}