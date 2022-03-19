package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.CurrencyDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.repository.CurrencyRepository;
import com.project.warehouse.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
CurrencyService currencyService;
    @Autowired
    CurrencyRepository currencyRepository;
    @GetMapping
    public String getCurrencyPage(Model model) {
        model.addAttribute("list", currencyRepository.findAll());
        return "currency/currency";
    }


    @GetMapping("/add")
    public String getSaveCurrency() {

        return "currency/currency-add";
    }

    @PostMapping("/add")
    public String saveCurrency(Model model, @ModelAttribute Currency currency) {
        currencyService.add(currency);
        return "redirect:/currency";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        currencyRepository.deleteById(id);
        return "redirect:/currency";
    }


    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {

        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent()) return "Xatolik!";
        model.addAttribute("edited", optionalCurrency.get());
        return "currency/currency-edit";
    }

    @PostMapping("/edit/{id}")
    public String editcurrency(@PathVariable Long id, @ModelAttribute CurrencyDto currencyDto) {
        ApiResponse response = currencyService.edit(id, currencyDto);
        System.out.println(response);
        return "redirect:/currency";
    }
}


