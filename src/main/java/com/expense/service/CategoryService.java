package com.expense.service;

import com.expense.entity.Category;
import com.expense.exceptionHandler.DuplicateResourceException;
import com.expense.exceptionHandler.ResourceNotFoundException;
import com.expense.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(
            CategoryRepository categoryRepository
    ) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {

        return categoryRepository.findAll();
    }

    public Category create(Category category) {

        if (categoryRepository.existsByName(
                category.getName())) {

            throw new DuplicateResourceException(
                    "Category already exists"
            );
        }

        return categoryRepository.save(category);
    }

    public Category update(
            Long id,
            Category categoryDetails
    ) {

        Category existingCategory =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found with id: " + id
                                )
                        );

        if (categoryRepository.existsByName(
                categoryDetails.getName())

                &&

                !existingCategory.getName()
                        .equals(categoryDetails.getName())) {

            throw new DuplicateResourceException(
                    "Category already exists"
            );
        }

        existingCategory.setName(
                categoryDetails.getName()
        );

        return categoryRepository.save(
                existingCategory
        );
    }

    public Category getById(Long id) {

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id
                        )
                );
    }

    public void delete(Long id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found with id: " + id
                                )
                        );

        categoryRepository.delete(category);
    }
}