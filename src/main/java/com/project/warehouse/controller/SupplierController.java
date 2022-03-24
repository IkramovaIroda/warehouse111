package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.SupplierDto;
import com.project.warehouse.entity.Supplier;
import com.project.warehouse.repository.SupplierRepository;
//import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

    @Controller
    @RequiredArgsConstructor
    @RequestMapping("/data/supplier")
    public class SupplierController {

        final
        SupplierService supplierService;
        final
        SupplierRepository supplierRepository;
//        final AuthService authService;

        @GetMapping
        public String getSupplierPage(Model model, HttpServletRequest req, HttpServletResponse res) {
//            if (authService.deleteTokenIf(req, res)) {
//                return "redirect:/auth/login?return_url="+req.getServletPath();
//            }
            model.addAttribute("list", supplierRepository.findAllByActiveTrue());
            return "data/supplier";
        }

        @PostMapping
        public String saveSupplier(@ModelAttribute Supplier supplier) {
            supplierService.add(supplier);
            return "redirect:/data/supplier";
        }

        @GetMapping("/delete/{id}")
        public String delete(@PathVariable Long id) {
            Optional<Supplier> byId = supplierRepository.findById(id);
            if (byId.isEmpty()) return "404";
            Supplier supplier = byId.get();
            supplier.setActive(false);
            supplierRepository.save(supplier);
            return "redirect:data/supplier";
        }

        @PostMapping("/edit/{id}")
        public String editSupplier(@PathVariable Long id, @ModelAttribute SupplierDto supplierDto) {
            ApiResponse response = supplierService.edit(supplierDto,id);
            System.out.println(response);
            return "redirect:/data/supplier";
        }
    }


