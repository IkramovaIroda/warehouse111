package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.CurrencyDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.repository.CurrencyRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/data/currency")
@RequiredArgsConstructor
public class CurrencyController {

    final CurrencyService currencyService;
    final CurrencyRepository currencyRepository;
    final AuthService authService;

    @GetMapping
    public String getCurrencyPage(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        model.addAttribute("list", currencyRepository.findAllByActiveTrue());
        return "data/currency";
    }

    @PostMapping
    public String saveCurrency(@ModelAttribute Currency currency, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        currencyService.add(currency);
        return "redirect:/data/currency";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Currency> byId = currencyRepository.findById(id);
        if (byId.isEmpty()) return "404";
        Currency currency = byId.get();
        currency.setActive(false);
        currencyRepository.save(currency);
        return "redirect:/currency";
    }

    @PostMapping("/edit/{id}")
    public String editCurrency(@PathVariable Long id, @ModelAttribute CurrencyDto currencyDto) {
        ApiResponse response = currencyService.edit(id, currencyDto);
        System.out.println(response);
        return "redirect:/currency";
    }
}


