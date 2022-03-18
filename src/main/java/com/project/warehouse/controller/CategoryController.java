package com.project.warehouse.controller;

import com.project.warehouse.dto.CategoryDto;
import com.project.warehouse.entity.Category;
import com.project.warehouse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/data/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryRepository categoryRepository;

    @GetMapping
    public String get(Model model){
        model.addAttribute("list", categoryRepository.findAllByActiveTrue());
        return "data/category";
    }

    @PostMapping
    public String add(@ModelAttribute CategoryDto categoryDto){
        if(categoryDto.getName().equalsIgnoreCase("#NULL"))return "redirect:/data/category";
        Category category=new Category();
        if(!categoryDto.getParentCategoryId().equalsIgnoreCase("#null")){
            Optional<Category> byId = categoryRepository.findById(Long.parseLong(categoryDto.getParentCategoryId()));
            if(byId.isEmpty()){
                return "redirect:/data/category";
            }
            category.setParentCategory(byId.get());
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return "redirect:/data/category";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/data/category";
        }
        byId.get().setActive(false);
        categoryRepository.save(byId.get());
        return "redirect:/data/category";
    }
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute CategoryDto categoryDto){
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/data/category";
        }
        Category category = byId.get();
        if (categoryDto.getParentCategoryId().equals("#null")) {
            category.setParentCategory(null);
        }else {
            Optional<Category> byId1 = categoryRepository.findById(Long.parseLong(categoryDto.getParentCategoryId()));
            if (byId1.isEmpty()) {
                return "redirect:/data/category";
            }
            category.setParentCategory(byId1.get());
        }
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return "redirect:/data/category";
    }
}
