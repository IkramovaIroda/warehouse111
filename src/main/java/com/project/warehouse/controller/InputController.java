package com.project.warehouse.controller;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.entity.Input;
import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/input")
@RequiredArgsConstructor
public class InputController {
    final InputRepository inputRepository;
    final InputService inputService;
    final InputProductRepository inputProductRepository;
    final ProductRepository productRepository;
    final WarehouseRepository warehouseRepository;
    final SupplierRepository supplierRepository;
    final CurrencyRepository currencyRepository;
    final AuthService authService;

    @GetMapping("/all")
    public String getInputs(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("inputList", inputRepository.findAllByActiveTrue());
        return "input/all";
    }

    @GetMapping("/getInput/{id}")
    public String getOneInput(@PathVariable Long id, Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Input> byId = inputRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        Input input = byId.get();
        List<InputProduct> byInput = inputProductRepository.findAllByInput_Id(id);
        model.addAttribute("inputProducts", byInput);
        model.addAttribute("input", input);
        return "input/products";
    }

    @GetMapping("/addInput")
    public String add(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("productList", productRepository.findAllByActiveTrue());
        model.addAttribute("supplierList", supplierRepository.findAllByActiveTrue());
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now().toString());
        return "input/input-add";
    }

    @SneakyThrows
    @PostMapping("/addInput")
    public String saveInput(@ModelAttribute InputDto inputDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        inputService.save(inputDto);
        return "redirect:/input/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Input> byId = inputRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        Input input = byId.get();
        input.setActive(false);
        inputRepository.save(input);
        return "redirect:/input/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("input", inputRepository.findById(id).get());
        model.addAttribute("inputProducts", inputProductRepository.findAllByInput_Id(id));
        model.addAttribute("supplierList", supplierRepository.findAllByActiveTrue());
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now());
        return "input/edit";
    }

    @PostMapping("/editInputSave/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute InputDto inputDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        inputService.edit(id, inputDto);
        return "redirect:/input/all";
    }
}
