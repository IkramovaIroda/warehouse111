package com.project.warehouse.service;

import com.project.warehouse.dto.OutputDto;
import com.project.warehouse.dto.OutputProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutputService {
    final
    OutputRepository outputRepository;
    final
    ClientRepository clientRepository;
    final
    WarehouseRepository warehouseRepository;
    final
    CurrencyRepository currencyRepository;
    final
    InputProductRepository inputProductRepository;
    final
    ProductRepository productRepository;
    final
    OutputProductRepository outputProductRepository;


    public Output saveOutput(OutputDto dto) {
        Long clientId = dto.getClientId();
        LocalDate date = dto.getDate();
        Long currencyId = dto.getCurrencyId();
        Integer factureNumber = dto.getFactureNumber();
        Long warehouseId = dto.getWarehouseId();

        Warehouse warehouse = warehouseRepository.findById(warehouseId).get();
        Currency currency = currencyRepository.findById(currencyId).get();
        Client client = clientRepository.findById(clientId).get();
        Output output = new Output();
        output.setCurrency(currency);
        output.setClient(client);
        output.setWarehouse(warehouse);
        output.setDate(date);
        output.setFactureNumber(factureNumber);
        return outputRepository.save(output);
    }

    public void saveOutputProducts(OutputDto dto, Output output) {
        Long clientId = dto.getClientId();
        LocalDate date = dto.getDate();
        Long currencyId = dto.getCurrencyId();
        Integer factureNumber = dto.getFactureNumber();
        Long warehouseId = dto.getWarehouseId();
        List<OutputProductDto> outputProductDtoList = dto.getOutputProductDtoList();

        Warehouse warehouse = warehouseRepository.findById(warehouseId).get();
        Currency currency = currencyRepository.findById(currencyId).get();
        Client client = clientRepository.findById(clientId).get();

        List<OutputProduct> outputProducts = new ArrayList<>();
        for (OutputProductDto outputProductDto : outputProductDtoList) {
            Long productId = outputProductDto.getProductId();
            Double amount = outputProductDto.getAmount();
            Double price = outputProductDto.getPrice();
            Product product = productRepository.findById(productId).get();
            OutputProduct outputProduct = new OutputProduct();
            outputProduct.setOutput(output);
            outputProduct.setProduct(product);
            outputProduct.setAmount(amount);
            outputProduct.setPrice(price);
            outputProducts.add(outputProduct);
        }
        outputProductRepository.saveAll(outputProducts);
    }

    public List<InputProduct>  addOutput( Output output) {
        Warehouse warehouse = output.getWarehouse();
        return inputProductRepository.findByInputActiveAndInput_Warehouse(true, warehouse);
    }

    public void saveEditOutput(Long id, OutputDto outputDto) {
        Long clientId = outputDto.getClientId();
        LocalDate date = outputDto.getDate();
        Integer factureNumber = outputDto.getFactureNumber();
        Long currencyId = outputDto.getCurrencyId();
        Long warehouseId = outputDto.getWarehouseId();

        Warehouse warehouse = warehouseRepository.findById(warehouseId).get();
        Currency currency = currencyRepository.findById(currencyId).get();
        Client client = clientRepository.findById(clientId).get();
        Output output = outputRepository.findById(id).get();
        output.setFactureNumber(factureNumber);
        output.setWarehouse(warehouse);
        output.setClient(client);
        output.setDate(date);
        output.setCurrency(currency);
        Output save = outputRepository.save(output);
        List<OutputProduct> outputProducts = outputProductRepository.findAllByOutput_Id(id);
        for (OutputProduct outputProduct : outputProducts) {
            outputProduct.setOutput(save);
        }
        outputProductRepository.saveAll(outputProducts);

    }

    public void saveEditOutputProducts(Long id, OutputDto outputDto) {
        Long clientId = outputDto.getClientId();
        LocalDate date = outputDto.getDate();
        Integer factureNumber = outputDto.getFactureNumber();
        Long currencyId = outputDto.getCurrencyId();
        Long warehouseId = outputDto.getWarehouseId();
        List<OutputProductDto> outputProductDtoList = outputDto.getOutputProductDtoList();

        Warehouse warehouse = warehouseRepository.findById(warehouseId).get();
        Currency currency = currencyRepository.findById(currencyId).get();
        Client client = clientRepository.findById(clientId).get();
        Output output = outputRepository.findById(id).get();
        List<OutputProduct> outputProducts = outputProductRepository.findAllByOutput_IdAndOutputActive(id, true);
        for (OutputProductDto productDto : outputProductDtoList) {
            Long productId = productDto.getProductId();
            Double price = productDto.getPrice();
            Double amount = productDto.getAmount();
            Long outputId = productDto.getOutputId();
            Product product = productRepository.findById(productId).get();

            OutputProduct outputProduct = new OutputProduct();
            outputProduct.setOutput(output);
            outputProduct.setProduct(product);
            outputProduct.setPrice(price);
            outputProduct.setAmount(amount);
            outputProducts.add(outputProduct);
        }
        outputProductRepository.saveAll(outputProducts);
    }
}
