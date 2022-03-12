package com.project.warehouse.service;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.dto.InputProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ProductRepository productRepository;

    public void save(InputDto inputDto) {
        List<InputProductDto> inputProductDtoList = inputDto.getInputProductDtoList();
        LocalDate date = inputDto.getDate();
        Long currencyId = inputDto.getCurrencyId();
        Integer factureNumber = inputDto.getFactureNumber();
        Long supplierId = inputDto.getSupplierId();
        Long warehouseId = inputDto.getWarehouseId();
        Optional<Currency> currencyOptional = currencyRepository.findById(currencyId);
        Currency currency = currencyOptional.get();
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);
        Warehouse warehouse = warehouseOptional.get();
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        Supplier supplier = supplierOptional.get();

        Input input = new Input();
        input.setFactureNumber(factureNumber);
        input.setCurrency(currency);
        input.setSupplier(supplier);
        input.setWarehouse(warehouse);
        input.setDate(date);
        Input saveInput = inputRepository.save(input);
        List<InputProduct> inputProducts = new ArrayList<>();
        for (InputProductDto dto : inputProductDtoList) {
            InputProduct inputProduct = new InputProduct();
            Long inputId = dto.getInputId();// 1-variant date frontenddan
            //2-variant date backendda beriladi
//            inputProduct.setInput(saveInput);
            Double amount = dto.getAmount();
            LocalDate expireDate = dto.getExpireDate();//1-variant date frontenddan
            //2-variant date backendda beriladi
//            inputProduct.setExpireDate(LocalDate.now());
            Double price = dto.getPrice();
            Long productId = dto.getProductId();
            Optional<Product> productOptional = productRepository.findById(productId);
            Product product = productOptional.get();

            inputProduct.setInput(saveInput);
            inputProduct.setProduct(product);
            inputProduct.setPrice(price);
            inputProduct.setAmount(amount);
            inputProduct.setExpireDate(date);
//            inputProduct.setExpireDate(LocalDate.now());
            inputProducts.add(inputProduct);
        }
          inputProductRepository.saveAll(inputProducts);
    }

    public void edit(Long id, InputDto inputDto) {
        Optional<Input> byId = inputRepository.findById(id);
        Input input = byId.get();
        LocalDate date = inputDto.getDate();
        Long currencyId = inputDto.getCurrencyId();
        Integer factureNumber = inputDto.getFactureNumber();
        Long supplierId = inputDto.getSupplierId();
        Long warehouseId = inputDto.getWarehouseId();
        Optional<Currency> currencyOptional = currencyRepository.findById(currencyId);
        Currency currency = currencyOptional.get();
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);
        Warehouse warehouse = warehouseOptional.get();
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        Supplier supplier = supplierOptional.get();

        input.setFactureNumber(factureNumber);
        input.setCurrency(currency);
        input.setSupplier(supplier);
        input.setWarehouse(warehouse);
        input.setDate(date);
        Input save = inputRepository.save(input);
//        List<InputProduct> inputProducts = inputProductRepository.findByInput(save);
        List<InputProduct> inputProducts = inputProductRepository.findByInput_Id(save.getId());

        for (InputProduct inputProduct : inputProducts) {
            inputProduct.setInput(save);
        }
        inputProductRepository.saveAll(inputProducts);

    }
}
