package com.project.warehouse.service;

import com.project.warehouse.dto.ProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    final AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public void save(ProductDto productDto) {
        String name = productDto.getName();
        Long categoryId = productDto.getCategoryId();
        Long measurementId = productDto.getMeasurementId();
//        Long photo_id = productDto.getPhoto_id();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())return;
        Category category = categoryOptional.get();
        Optional<Measurement> measurementOptional = measurementRepository.findById(measurementId);
        if(measurementOptional.isEmpty())return;
        Measurement measurement = measurementOptional.get();
        Product product = new Product();
        product.setCategory(category);
        product.setMeasurement(measurement);
        product.setName(productDto.getName());
        if(!productDto.getPhoto().isEmpty()){
            Attachment attachment=new Attachment();
            attachment.setName(productDto.getPhoto().getOriginalFilename());
            attachment.setSize(productDto.getPhoto().getSize());
            attachment.setContent_type(productDto.getPhoto().getContentType());
            Attachment save = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent=new AttachmentContent();
            attachmentContent.setAttachment(save);
            attachmentContent.setBytes(productDto.getPhoto().getBytes());
            attachmentContentRepository.save(attachmentContent);
            product.setPhoto(attachment);
        }
        productRepository.save(product);

    }

    @SneakyThrows
    public void edit(Long id, ProductDto productDto) {
        Optional<Product> byId = productRepository.findById(id);
        if(byId.isEmpty())return;
        Product product = byId.get();
        Long categoryId = productDto.getCategoryId();
        Long measurementId = productDto.getMeasurementId();
        String name=productDto.getName();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())return;
        Category category = categoryOptional.get();
        Optional<Measurement> measurementOptional = measurementRepository.findById(measurementId);
        if(measurementOptional.isEmpty())return;;
        Measurement measurement = measurementOptional.get();
        product.setName(name);
        if(productDto.getPhoto().isEmpty()){
            product.setPhoto(null);
        }else {
            if(product.getPhoto()==null){
                Attachment attachment=new Attachment();
                attachment.setName(productDto.getPhoto().getOriginalFilename());
                attachment.setSize(productDto.getPhoto().getSize());
                attachment.setContent_type(productDto.getPhoto().getContentType());
                Attachment save = attachmentRepository.save(attachment);
                AttachmentContent attachmentContent=new AttachmentContent();
                attachmentContent.setAttachment(save);
                attachmentContent.setBytes(productDto.getPhoto().getBytes());
                attachmentContentRepository.save(attachmentContent);
                product.setPhoto(attachment);
            }else {
                Optional<Attachment> optionalAttachment = attachmentRepository.findById(product.getPhoto().getId());
                if (optionalAttachment.isEmpty()) return;
                Attachment attachment = optionalAttachment.get();
                attachment.setName(productDto.getPhoto().getOriginalFilename());
                attachment.setSize(productDto.getPhoto().getSize());
                attachment.setContent_type(productDto.getPhoto().getContentType());
                Attachment save = attachmentRepository.save(attachment);
                Optional<AttachmentContent> optionalAttachmentContent =
                        attachmentContentRepository.findByAttachment_id(save.getId());
                AttachmentContent attachmentContent;
                if (optionalAttachmentContent.isEmpty()) {
                    attachmentContent = new AttachmentContent();
                } else {
                    attachmentContent = optionalAttachmentContent.get();
                }
                attachmentContent.setBytes(productDto.getPhoto().getBytes());
                attachmentContentRepository.save(attachmentContent);
            }
        }
        product.setCategory(category);
        product.setMeasurement(measurement);
        Product save = productRepository.save(product);


    }

    public HttpEntity<?> getPhoto(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optionalProduct.get();
        if(product.getPhoto()==null){
            return ResponseEntity.status(302)
                    .header("Location", "/assets/icons/not-image.png")
                    .build();
        }
        Attachment attachment = product.getPhoto();
        Optional<AttachmentContent> optionalAttachmentContent =
                attachmentContentRepository.findByAttachment_id(attachment.getId());
        if(optionalAttachmentContent.isEmpty()){
            return ResponseEntity.status(302)
                    .header("Location", "/assets/icons/not-image.png").build();
        }
        return ResponseEntity.ok().header("Content-Type",attachment.getContent_type())
                .body(optionalAttachmentContent.get().getBytes());
    }

}

