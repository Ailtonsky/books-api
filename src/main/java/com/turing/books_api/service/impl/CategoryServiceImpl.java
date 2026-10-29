package com.turing.books_api.service.impl;

import com.turing.books_api.dto.category.CategoryResponse;
import com.turing.books_api.repository.CategoryRepository;
import com.turing.books_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    //filtro del catalogo
    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAllByOrderByNameAsc()
                .stream().map(CategoryResponse::from)
                .toList();
    }
}