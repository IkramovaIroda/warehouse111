package com.project.warehouse.service;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.dto.InputProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
            Double amount = dto.getAmount();
            LocalDate expireDate = LocalDate.parse(dto.getExpireDate());
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
        List<InputProduct> inputProducts = inputProductRepository.findAllByInput_Id(save.getId());
        List<InputProductDto> inputProductDtos = inputDto.getInputProducts();
        for (InputProduct inputProduct : inputProducts) {
            List<InputProductDto> collect = inputProductDtos.stream().filter(inputProductDto ->
                    Objects.equals(inputProductDto.getInputProductId(), inputProduct.getId())).toList();
            if (collect.size() == 0) {
                inputProductRepository.deleteById(inputProduct.getId());
                continue;
            }
            inputProduct.setInput(save);
            inputProduct.setAmount(collect.get(0).getAmount());
            inputProduct.setPrice(collect.get(0).getPrice());
            inputProduct.setExpireDate(LocalDate.parse(collect.get(0).getExpireDate()));
            inputProductRepository.save(inputProduct);
        }
    }

}
