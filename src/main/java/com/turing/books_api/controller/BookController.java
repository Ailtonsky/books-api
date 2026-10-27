package com.turing.books_api.controller;

import com.turing.books_api.dto.book.BookResponse;
import com.turing.books_api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //Aqui pondre el primer endpoint que es paginar todos los libros
    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(@PageableDefault(size = 8, sort = "id")
                                                              Pageable pageable) {//la anotacion es para poner por default las 8 paginas en cada paginacion

        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }


}
