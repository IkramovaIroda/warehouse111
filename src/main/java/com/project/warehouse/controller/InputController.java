package com.project.warehouse.controller;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.dto.InputProductDto;
import com.project.warehouse.entity.Input;
import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.warehouse.service.InputService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    static Input input = new Input();
    final AuthService authService;

    @GetMapping("/all")
    public String getInputs(Model model, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        model.addAttribute("inputList", inputRepository.findAll());
        return "input/input";
    }
    @GetMapping("/getInput/{id}")
    public String getOneInput(@PathVariable Long id, Model model, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        Optional<Input> byId = inputRepository.findById(id);
         input = byId.get();
        model.addAttribute("input", input);
        return "input/input";
    }

    @GetMapping("/getInput/getInputProducts")
    public String getInputProducts(Model model, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        List<InputProduct> byInput = inputProductRepository.findByInput(input);
        model.addAttribute("inputProducts", byInput);
        return "input/getProducts";
    }

    @GetMapping("/addInput")
    public String add(Model model, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        model.addAttribute("productList", productRepository.findAllByActiveTrue());
        model.addAttribute("supplierList", supplierRepository.findAllByActiveTrue());
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now().toString());
        return "input/input-add";
    }

    @SneakyThrows
    @PostMapping("/addInput")
    public String saveInput(@ModelAttribute InputDto inputDto, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        inputService.save(inputDto);
        return "redirect:/input/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        Optional<Input> byId = inputRepository.findById(id);
        Input input = byId.get();
        input.setActive(false);
        return "redirect:/input/all";
    }

    @GetMapping("/getInput/editInput/{id}")
    public String edit(Model model, @PathVariable Long id, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        model.addAttribute("replaceableInput", inputRepository.findById(id).get());
        model.addAttribute("supplierList", supplierRepository.findAllByActiveTrue());
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        return "edit";
    }

    @PostMapping("/editInputSave/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute InputDto inputDto, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        inputService.edit(id, inputDto);
        return "redirect:/input/all";
    }

    @GetMapping("/getInput/getInputProducts/editInputProducts/{id}")
    public String editInputProducts(@PathVariable Long id, Model model, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
          model.addAttribute("products", inputProductRepository.findById(id).get());
          model.addAttribute("productList", productRepository.findAllByActiveTrue());
          return "input/editInputProducts";
    }
    @PostMapping("/getInput/getInputProducts/editInputProducts/{id}")
    public String saveEditInputProducts(@PathVariable Long id, @ModelAttribute InputProductDto inputProduct, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        inputService.saveEditInputProducts(id, inputProduct);
        return "redirect:/input/all";
    }
    @GetMapping("/getInput/getInputProducts/deleteInputProducts/{id}")
    public String deleteInputProducts(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res){
        if (authService.deleteTokenIf(req, res)) {return "secured-page";}
        inputProductRepository.deleteById(id);
        return "/getInput/getInputProducts";
    }
}
