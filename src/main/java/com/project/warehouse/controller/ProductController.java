package com.project.warehouse.controller;

import com.project.warehouse.dto.ProductDto;
import com.project.warehouse.entity.Product;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/data/product")
@RequiredArgsConstructor
public class ProductController {
    final ProductService productService;
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;
    final MeasurementRepository measurementRepository;
    final AttachmentRepository attachmentRepository;
    final AttachmentContentRepository attachmentContentRepository;
    final AuthService authService;

    @GetMapping
    public String getProductPage(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        List<Product> all = productRepository.findAllByActiveTrue();
        model.addAttribute("list", all);
        model.addAttribute("categories", categoryRepository.findAllByActiveTrue());
        model.addAttribute("measurements", measurementRepository.findAllByActiveTrue());
        return "data/product";
    }

    @SneakyThrows
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute ProductDto productDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        productService.save(productDto);
        return "redirect:/data/product";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        Product product = byId.get();
        product.setActive(false);
        productRepository.save(product);
        return "redirect:/data/product";
    }

    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute ProductDto productDto,
                           HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        productService.edit(id, productDto);
        return "redirect:/data/product";
    }

    @ResponseBody
    @GetMapping("/{id}/photo")
    public HttpEntity<?> getPhoto(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return ResponseEntity.notFound().build();
        }
        return productService.getPhoto(id);
    }

}
