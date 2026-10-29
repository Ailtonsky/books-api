package com.turing.books_api.repository;

import com.turing.books_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Aqui busco por nombre, pongo Optional porque puede existir o no
    Optional<Category> findByName(String name);

    //Aqui verifico si ya existe una categoria con su nombre y no se trae el objeto completod e la bd
    boolean existsByName(String name);

    //este sera para ordenar las categorias para el filtro del catalogo
    List<Category> findAllByOrderByNameAsc();
}
