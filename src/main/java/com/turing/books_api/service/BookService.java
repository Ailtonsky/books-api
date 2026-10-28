package com.turing.books_api.service;

import com.turing.books_api.dto.book.BookRequest;
import com.turing.books_api.dto.book.BookResponse;
import com.turing.books_api.dto.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//aqui me basare en las historias de usuario apra hacer los metodos
public interface BookService {
    PageResponse<BookResponse> getBooks(String search, Long categoryId, Pageable pageable);

    BookResponse getById(Long id);

    BookResponse create(BookRequest request);

    BookResponse update(Long id, BookRequest request);

    void delete(Long id);
}
