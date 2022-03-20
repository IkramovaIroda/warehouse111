package com.project.warehouse.service;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.dto.InputProductDto;
import com.project.warehouse.dto.ProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    final
    ProductRepository productRepository;
    final
    CategoryRepository categoryRepository;
    final
    MeasurementRepository measurementRepository;
    final
    AttachmentRepository attachmentRepository;

    public void save(ProductDto productDto) {
        String name = productDto.getName();
        Long categoryId = productDto.getCategoryId();
        Long measurementId = productDto.getMeasurementId();
//        Long photo_id = productDto.getPhoto_id();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        Category category = categoryOptional.get();
        Optional<Measurement> measurementOptional = measurementRepository.findById(measurementId);
        Measurement measurement = measurementOptional.get();
//        Optional<Attachment> attachmentOptional = attachmentRepository.findById(photo_id);
//        Attachment attachment = attachmentOptional.get();

        Product product = new Product();
        product.setCategory(category);
        product.setMeasurement(measurement);
//        product.setAttachment(attachment);
        product.setName(productDto.getName());
//        product.setCode(productDto.getCode());
        Product save = productRepository.save(product);

    }

    public void edit(Long id, ProductDto productDto) {
        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.get();
        Long categoryId = productDto.getCategoryId();
        Long measuremantId = productDto.getMeasurementId();
        String name=productDto.getName();
//        Long photo_id = productDto.getPhoto_id();
//        Long code = productDto.getCode();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.get();
        Optional<Measurement> measurementOptional = measurementRepository.findById(measuremantId);
        Measurement measurement = measurementOptional.get();
//        Optional<Attachment> attachmentOptional = attachmentRepository.findById(photo_id);
//        Attachment attachment = attachmentOptional.get();

        product.setName(name);
//        product.setAttachment(attachment);
//        product.setCode(code);
        product.setCategory(category);
        product.setMeasurement(measurement);
        Product save = productRepository.save(product);


    }

}

