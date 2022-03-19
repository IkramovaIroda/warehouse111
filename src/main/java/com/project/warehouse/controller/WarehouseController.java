package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.WarehouseDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.WarehouseRepository;
import com.project.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/data/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    final
    WarehouseService warehouseService;
    final
    WarehouseRepository warehouseRepository;
    @GetMapping
    public String getWarehousePage(Model model) {

        model.addAttribute("list", warehouseRepository.findAllByActiveTrue());
        return "data/warehouse";
    }

    @PostMapping
    public String saveWarehouse(Model model, @ModelAttribute Warehouse warehouse) {
        warehouseService.add(warehouse);
        return "redirect:/data/warehouse";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Warehouse> byId = warehouseRepository.findById(id);
        if (byId.isEmpty()) return "404";
        Warehouse warehouse = byId.get();
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);
        return "redirect:/data/warehouse";
    }

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, @ModelAttribute WarehouseDto warehouseDto) {
        ApiResponse response = warehouseService.edit(id, warehouseDto);
        System.out.println(response);
        return "redirect:/data/warehouse";
    }
}


