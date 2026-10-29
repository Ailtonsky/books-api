package com.turing.books_api.service.impl;

import com.turing.books_api.dto.book.BookRequest;
import com.turing.books_api.dto.book.BookResponse;
import com.turing.books_api.dto.common.PageResponse;
import com.turing.books_api.entity.Book;
import com.turing.books_api.entity.Category;
import com.turing.books_api.exception.DuplicateResourceException;
import com.turing.books_api.exception.ResourceNotFoundException;
import com.turing.books_api.repository.BookRepository;
import com.turing.books_api.repository.CategoryRepository;
import com.turing.books_api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//y aqui viene toda la logica de negoio
@Service//le indicamos que es un service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

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

    @Override
    @Transactional(readOnly = true)
    public BookResponse getById(Long id) {
        Book book = bookRepository.findWithCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        return BookResponse.from(book);
    }


    @Override
    @Transactional
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException("Ya existe un libro con el ISBN: " + request.isbn());
        }

        Book book = new Book();
        applyRequest(book, request);

        return BookResponse.from(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = bookRepository.findWithCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        // El ISBN puede quedarse igual, pero no puede repetir el de OTRO libro
        if (bookRepository.existsByIsbnAndIdNot(request.isbn(), id)) {
            throw new DuplicateResourceException("Ya existe otro libro con el ISBN: " + request.isbn());
        }

        applyRequest(book, request);

        return BookResponse.from(bookRepository.save(book));
    }

    //eliminar un libro
    @Override
    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + id);
        }

        bookRepository.deleteById(id);
    }

    // Copio los datos del request a la entidad y sirve para create y update
    private void applyRequest(Book book, BookRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con id: " + request.categoryId()));

        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setDescription(request.description());
        book.setPublicationYear(request.publicationYear());
        book.setPages(request.pages());
        book.setPrice(request.price());
        book.setCoverUrl(request.coverUrl());
        book.setCategory(category);
    }

}

