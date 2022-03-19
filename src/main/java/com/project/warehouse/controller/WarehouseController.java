package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.WarehouseDto;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.WarehouseRepository;
import com.project.warehouse.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        warehouseRepository.deleteById(id);
        return "redirect:/data/warehouse";
    }
//    @PostMapping("/edit/{id}")
//    public String editSave(@ModelAttribute WarehouseDto dto, @PathVariable Long id){
//        warehouseService.edit(dto, id);
//        return "redirect:/warehouse";
//    }

//    @PostMapping("/edit/{id}")
//    public ApiResponse edit(@PathVariable Long id, @RequestBody Warehouse warehouse) {
//        ApiResponse response = warehouseService.update(id, warehouse);
//        return response;
//    }
//    @GetMapping("/edit/{id}")
//    public String editPage(@PathVariable Long id, Model model) {
//
//        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
//        if (!optionalWarehouse.isPresent()) return "Xatolik!";
//
//    //        departmentRepository.getById()
//        model.addAttribute("edited", optionalWarehouse.get());
//        return "data/warehouse-edit";
//    }

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, @ModelAttribute WarehouseDto warehouseDto) {
        ApiResponse response = warehouseService.edit(id, warehouseDto);
        System.out.println(response);
        return "redirect:/data/warehouse";
    }
}


