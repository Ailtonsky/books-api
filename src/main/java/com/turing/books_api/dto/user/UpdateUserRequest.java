package com.turing.books_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        //esta anotacion se ocupa para obligar al usuario a poner el username y que no lo deje vacio
        @NotBlank(message = "El username es obligatorio")
        //aqui me baso en la entity
        @Size(max = 50)
        String username
) {
}