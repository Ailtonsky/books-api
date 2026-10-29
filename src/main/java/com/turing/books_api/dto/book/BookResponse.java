package com.turing.books_api.dto.book;

import com.turing.books_api.dto.category.CategoryResponse;
import com.turing.books_api.entity.Book;

import java.math.BigDecimal;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        String description,
        Short publicationYear,
        Integer pages,
        BigDecimal price,
        String coverUrl,
        CategoryResponse category
) {
    public static BookResponse from (Book book){
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getDescription(),
                book.getPublicationYear(),
                book.getPages(),
                book.getPrice(),
                book.getCoverUrl(),
                CategoryResponse.from(book.getCategory())
        );
    }
}
