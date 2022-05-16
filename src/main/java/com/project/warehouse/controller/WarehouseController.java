package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.WarehouseDto;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.WarehouseRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Controller
@RequestMapping("/data/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    final
    WarehouseService warehouseService;
    final
    WarehouseRepository warehouseRepository;
    final AuthService authService;

    @GetMapping
    public String getWarehousePage(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("list", warehouseRepository.findAllByActiveTrue());
        return "data/warehouse";
    }

    @PostMapping
    public String saveWarehouse(Model model, @ModelAttribute Warehouse warehouse, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        warehouseService.add(warehouse);
        return "redirect:/data/warehouse";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Warehouse> byId = warehouseRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        Warehouse warehouse = byId.get();
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);
        return "redirect:/data/warehouse";
    }

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, @ModelAttribute WarehouseDto warehouseDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        ApiResponse response = warehouseService.edit(id, warehouseDto);
        return "redirect:/data/warehouse";
    }
}


