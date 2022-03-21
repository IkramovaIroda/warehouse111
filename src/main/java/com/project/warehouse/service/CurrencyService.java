package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.CurrencyDto;
import com.project.warehouse.dto.WarehouseDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.CurrencyRepository;
import com.project.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    final
    CurrencyRepository currencyRepository;

    public ApiResponse add(Currency currency) {
        Currency save = currencyRepository.save(currency);
        return new ApiResponse("Saved", true, save);
    }
    public ApiResponse edit(Long id, CurrencyDto currencyDto) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        Currency currency = optionalCurrency.get();


        currency.setName(currencyDto.getName());

        currencyRepository.save(currency);
        return new ApiResponse("Updated!", true);
    }
}
