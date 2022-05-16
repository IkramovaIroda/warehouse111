package com.project.warehouse.service;

import com.project.warehouse.dto.CategoryDto;
import com.project.warehouse.entity.Category;
import com.project.warehouse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    final CategoryRepository categoryRepository;

    public void add(CategoryDto categoryDto) {
        if (categoryDto.getName().equalsIgnoreCase("#NULL")) return;
        Category category = new Category();
        if (!categoryDto.getParentCategoryId().equalsIgnoreCase("#null")) {
            Optional<Category> byId = categoryRepository.findById(Long.parseLong(categoryDto.getParentCategoryId()));
            if (byId.isEmpty()) {
                return;
            }
            category.setParentCategory(byId.get());
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    public void edit(CategoryDto categoryDto, Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        Category category = byId.get();
        if (categoryDto.getParentCategoryId().equals("#null")) {
            category.setParentCategory(null);
        } else {
            Optional<Category> byId1 = categoryRepository.findById(Long.parseLong(categoryDto.getParentCategoryId()));
            if (byId1.isEmpty()) {
                return;
            }
            category.setParentCategory(byId1.get());
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
    }

    public void delete(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        byId.get().setActive(false);
        categoryRepository.save(byId.get());
    }
}
