package com.project.warehouse.controller;

import com.project.warehouse.dto.SupplierDto;
import com.project.warehouse.entity.Supplier;
import com.project.warehouse.repository.SupplierRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/supplier")
public class SupplierController {

    final
    SupplierService supplierService;
    final
    SupplierRepository supplierRepository;
    final AuthService authService;

    @GetMapping
    public String getSupplierPage(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        model.addAttribute("list", supplierRepository.findAllByActiveTrue());
        return "users/supplier";
    }

    @PostMapping
    public String saveSupplier(@ModelAttribute SupplierDto supplierDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        supplierService.add(supplierDto);
        return "redirect:/users/supplier";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        supplierService.delete(id);
        return "redirect:/users/supplier";
    }

    @PostMapping("/edit/{id}")
    public String editSupplier(@PathVariable Long id, @ModelAttribute SupplierDto supplierDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)){return "secured-page";}
        supplierService.edit(supplierDto,id);
        return "redirect:/users/supplier";
    }
}


