package com.project.warehouse.controller;


import com.project.warehouse.dto.ProductDto;
import com.project.warehouse.entity.Product;
import com.project.warehouse.repository.AttachmentRepository;
import com.project.warehouse.repository.CategoryRepository;
import com.project.warehouse.repository.MeasurementRepository;
import com.project.warehouse.repository.ProductRepository;
import com.project.warehouse.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/data/product")
@RequiredArgsConstructor
public class ProductController {
    final
    ProductService productService;
    final
    ProductRepository productRepository;
    final
    CategoryRepository categoryRepository;
    final
    MeasurementRepository measurementRepository;
    final
    AttachmentRepository attachmentRepository;
    @GetMapping
    public String getProductPage(Model model) {
        List<Product> all = productRepository.findAllByActiveTrue();
        model.addAttribute("list", all);
        model.addAttribute("categories", categoryRepository.findAllByActiveTrue());
        model.addAttribute("measurements", measurementRepository.findAllByActiveTrue());
        return "data/product";
    }

    @GetMapping("/addProduct")
    public String add(Model model, ProductDto productDto){
        model.addAttribute("categoryList", categoryRepository.findAllByActiveTrue());
        model.addAttribute("measurementList", measurementRepository.findAllByActiveTrue());
        model.addAttribute("attachmentList", attachmentRepository.findAll());
        model.addAttribute("productdto", productDto);
        return "product/product-add";
    }

    @SneakyThrows
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute ProductDto productDto){
        productService.save(productDto);
        return "redirect:/data/product";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) return "404";
        Product product = byId.get();
        product.setActive(false);
        productRepository.save(product);
        return "redirect:/data/product";
    }



//    @GetMapping("/edit/{id}")
//    public String edit(Model model, @PathVariable Long id){
//        model.addAttribute("edited", productRepository.findById(id).get());
//        model.addAttribute("categoryList", categoryRepository.findAllByActiveTrue());
//        model.addAttribute("measurementList", measurementRepository.findAllByActiveTrue());
//        model.addAttribute("attachmentList", attachmentRepository.findAllByActiveTrue());
//        return "product/product-edit";
//    }

    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute ProductDto productDto){
        productService.edit(id, productDto);
        return "redirect:/data/product";
    }

}
