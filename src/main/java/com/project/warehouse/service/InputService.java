package com.project.warehouse.service;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.dto.InputProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InputService {

    final
    InputRepository inputRepository;
    final
    InputProductRepository inputProductRepository;
    final
    CurrencyRepository currencyRepository;
    final
    WarehouseRepository warehouseRepository;
    final
    SupplierRepository supplierRepository;
    final
    ProductRepository productRepository;

    public void save(InputDto inputDto) {
        List<InputProductDto> inputProductDtoList = inputDto.getInputProducts();
        LocalDate date = LocalDate.parse(inputDto.getDate());
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
            LocalDate expireDate = LocalDate.parse(dto.getExpireDate());//1-variant date frontenddan
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
            inputProduct.setExpireDate(expireDate);
//            inputProduct.setExpireDate(LocalDate.now());
            inputProducts.add(inputProduct);
        }
          inputProductRepository.saveAll(inputProducts);
    }

    public void edit(Long id, InputDto inputDto) {
        Optional<Input> byId = inputRepository.findById(id);
        Input input = byId.get();
        LocalDate date = LocalDate.parse(inputDto.getDate());
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

    public void saveEditInputProducts(Long id, InputProductDto inputProductDto) {
        InputProduct inputProduct = inputProductRepository.findById(id).get();
        Long productId = inputProductDto.getProductId();
        Double price = inputProductDto.getPrice();
        Double amount = inputProductDto.getAmount();
        LocalDate expireDate = LocalDate.parse(inputProductDto.getExpireDate());
        inputProduct.setProduct(productRepository.findById(id).get());
        inputProduct.setAmount(amount);
        inputProduct.setPrice(price);
        inputProduct.setExpireDate(expireDate);
        inputProductRepository.save(inputProduct);
    }
}
