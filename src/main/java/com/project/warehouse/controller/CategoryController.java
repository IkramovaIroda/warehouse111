package com.project.warehouse.controller;

import com.project.warehouse.dto.CategoryDto;
import com.project.warehouse.entity.Category;
import com.project.warehouse.repository.CategoryRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/data/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryRepository categoryRepository;
    final AuthService authService;
    final CategoryService categoryService;

    @GetMapping
    public String get(Model model, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        model.addAttribute("list", categoryRepository.findAllByActiveTrue());
        return "data/category";
    }

    @PostMapping
    public String add(@ModelAttribute CategoryDto categoryDto, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        categoryService.add(categoryDto);
        return "redirect:/data/category";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        categoryService.delete(id);
        return "redirect:/data/category";
    }
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute CategoryDto categoryDto, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        categoryService.edit(categoryDto, id);
        return "redirect:/data/category";
    }
}
