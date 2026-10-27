package com.turing.books_api.service.impl;

import com.turing.books_api.dto.book.BookResponse;
import com.turing.books_api.dto.common.PageResponse;
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
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    // Obtiene el catálogo paginado; la búsqueda por título y el filtro por categoría son opcionalea
    @Override
    @Transactional(readOnly = true)//esto es para que sea solo de lectura
    public PageResponse<BookResponse> getBooks(String search, Long categoryId, Pageable pageable) {
        // Si no hay busqueda se usa "" para que el LIKE '%%' traiga todos los títulos
        // y no se mande null, si llegase a haber texto, se le quitan los espacios
        String term;
        if (search == null) {
            term = "";
        } else {
            term = search.trim();
        }


        //lo que si es que pagina los objetos que se encuentren si o si
        Page<BookResponse> page = bookRepository//aqui hace la consulta en la base de datos, con el filtro y la paginación.
                .search(term, categoryId, pageable)
                .map(BookResponse::from);

        return PageResponse.from(page);
    }


}
