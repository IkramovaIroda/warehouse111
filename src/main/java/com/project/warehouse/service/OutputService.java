package com.project.warehouse.service;

import com.project.warehouse.dto.OutputDto;
import com.project.warehouse.dto.OutputProductDto;
import com.project.warehouse.entity.*;
import com.project.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public void saveOutput(OutputDto dto) {
        Output output=new Output();
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId()).get();
        Currency currency = currencyRepository.findById(dto.getCurrencyId()).get();
        Client client = clientRepository.findById(dto.getClientId()).get();
        output.setCurrency(currency);
        output.setClient(client);
        output.setWarehouse(warehouse);
        output.setDate(LocalDate.parse(dto.getDate()));
        output.setFactureNumber(dto.getFactureNumber());
        Output save = outputRepository.save(output);
        List<OutputProductDto> outputProductDtoList = dto.getProductList();
        for (OutputProductDto outputProductDto : outputProductDtoList) {
            InputProduct inputProduct=inputProductRepository.findById(outputProductDto.getInputId()).get();
            Long productId = inputProduct.getProduct().getId();
            Double amount = outputProductDto.getAmount();
            Double price = inputProduct.getPrice();
            Product product = productRepository.findById(productId).get();
            OutputProduct outputProduct = new OutputProduct();
            outputProduct.setOutput(save);
            outputProduct.setProduct(product);
            outputProduct.setAmount(amount);
            outputProduct.setPrice(price);
            outputProductRepository.save(outputProduct);
            inputProduct.setAmount(inputProduct.getAmount()-outputProduct.getAmount());
            inputProductRepository.save(inputProduct);
        }

    }

    public void delete(Long id) {
        Optional<Output> byId = outputRepository.findById(id);
        if(byId.isEmpty())return;
        Output output =byId.get();
        output.setActive(false);
        outputRepository.save(output);
        List<OutputProduct> allByOutput_id = outputProductRepository.findAllByOutput_Id(id);
        for (OutputProduct outputProduct : allByOutput_id) {
            List<InputProduct> inputProducts = inputProductRepository.findByProduct_idAndPriceAndAmount(
                    outputProduct.getProduct().getId(), outputProduct.getPrice(), outputProduct.getAmount());
            if(inputProducts.size()==0)continue;
            InputProduct inputProduct = inputProducts.get(0);
            inputProduct.setAmount(inputProduct.getAmount()+outputProduct.getAmount());
            inputProductRepository.save(inputProduct);
        }
    }
}
