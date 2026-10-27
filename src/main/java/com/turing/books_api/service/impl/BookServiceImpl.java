package com.turing.books_api.service.impl;

import com.turing.books_api.dto.book.BookResponse;
import com.turing.books_api.repository.BookRepository;
import com.turing.books_api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//y aqui viene toda la logica de negoio
@Service//le indicamos que es un service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    //aqui se implementa el metodo para obtener todos los libros, cualquiera puede acceder a elloos
    @Override
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookResponse::from);
    }


}
