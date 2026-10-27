package com.turing.books_api.service;

import com.turing.books_api.dto.book.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//aqui me basare en las historias de usuario apra hacer los metodos
public interface BookService {
    Page<BookResponse> getAllBooks(Pageable pageable);

}
