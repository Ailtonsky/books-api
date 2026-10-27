package com.turing.books_api.repository;

import com.turing.books_api.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    //aqui investigue que con @EntityGraph,, Hibernate agrega un JOIN y trae tod y nos ayuda a resolver lo de muchos a uno
    @EntityGraph(attributePaths = "category")
    @Query(value = """
            SELECT b FROM Book b
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
              AND (:categoryId IS NULL OR b.category.id = :categoryId)
            """,
            countQuery = """
                    SELECT COUNT(b) FROM Book b
                    WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))
                      AND (:categoryId IS NULL OR b.category.id = :categoryId)
                    """)
    Page<Book> search(@Param("search") String search,
                      @Param("categoryId") Long categoryId,
                      Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Optional<Book> findWithCategoryById(Long id);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);
}
