package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.WarehouseDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.WarehouseRepository;
import com.project.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    WarehouseRepository warehouseRepository;
    @GetMapping
    public String getWarehousePage(Model model) {
        List<Warehouse> all = warehouseRepository.findAllByActiveTrue();
        model.addAttribute("list",all);
        return "warehouse/warehouse";
    }


    @GetMapping("/add")
    public String getSaveWarehouse() {

        return "warehouse/warehouse-add";
    }

    @PostMapping("/add")
    public String saveWarehouse(Model model, @ModelAttribute Warehouse warehouse) {
        warehouseService.add(warehouse);
        return "redirect:/warehouse";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Warehouse> byId = warehouseRepository.findById(id);
        if (byId.isEmpty()) return "404";
        Warehouse warehouse = byId.get();
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);
        return "redirect:/warehouse";
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
@GetMapping("/edit/{id}")
public String editPage(@PathVariable Long id, Model model) {

    Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
    if (!optionalWarehouse.isPresent()) return "Xatolik!";

//        departmentRepository.getById()
    model.addAttribute("edited", optionalWarehouse.get());
    return "warehouse/warehouse-edit";
}

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, @ModelAttribute WarehouseDto warehouseDto) {
        ApiResponse response = warehouseService.edit(id, warehouseDto);
        System.out.println(response);
        return "redirect:/warehouse";
    }
}


