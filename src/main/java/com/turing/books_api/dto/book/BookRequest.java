package com.turing.books_api.dto.book;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
public record BookRequest(

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 200, message = "El título no puede exceder 200 caracteres")
        String title,

        @NotBlank(message = "El autor es obligatorio")
        @Size(max = 150, message = "El autor no puede exceder 150 caracteres")
        String author,

        @NotBlank(message = "El ISBN es obligatorio")
        String isbn,

        @Size(max = 5000, message = "La descripción no puede exceder 5000 caracteres")
        String description,

        @Min(value = 1450, message = "El año de publicación debe ser mayor o igual a 1450")
        @Max(value = 2030, message = "El año de publicación debe ser menor o igual a 2030")
        Short publicationYear,

        @Positive(message = "El número de páginas debe ser mayor a 0")
        Integer pages,

        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio no puede ser negativo")
        @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 enteros y 2 decimales")
        BigDecimal price,

        @Size(max = 500, message = "La URL de la portada no puede exceder 500 caracteres")
        String coverUrl,

        @NotNull(message = "La categoría es obligatoria")
        @Positive(message = "El id de categoría debe ser válido")
        Long categoryId
) {
}