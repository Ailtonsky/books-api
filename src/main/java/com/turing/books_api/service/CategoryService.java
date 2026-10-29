package com.turing.books_api.service;

import com.turing.books_api.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories();
}
