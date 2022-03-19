package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.MeasurementDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.entity.Measurement;
import com.project.warehouse.repository.MeasurementRepository;
import com.project.warehouse.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/measurement")
public class MeasurementConrtoller {

    @Autowired
    MeasurementService measurementService;
    @Autowired
    MeasurementRepository measurementRepository;
    @GetMapping
    public String getMeasurementPage(Model model) {
        List<Measurement> all = measurementRepository.findAllByActiveTrue();
        model.addAttribute("list", all);
        return "measurement/measurement";
    }


    @GetMapping("/add")
    public String getSaveMeasurement() {

        return "measurement/measurement-add";
    }

    @PostMapping("/add")
    public String saveMeasurement(Model model, @ModelAttribute Measurement measurement) {
        measurementService.add(measurement);
        return "redirect:/measurement";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (byId.isEmpty()) return "404";
        Measurement measurement = byId.get();
        measurement.setActive(false);
        measurementRepository.save(measurement);
        return "redirect:/measurement";
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

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent()) return "Xatolik!";

//        departmentRepository.getById()
        model.addAttribute("edited", optionalMeasurement.get());
        return "measurement/measurement-edit";
    }

    @PostMapping("/edit/{id}")
    public String editmeasurement(@PathVariable Long id, @ModelAttribute MeasurementDto measurementDto) {
        ApiResponse response = measurementService.edit(id, measurementDto);
        System.out.println(response);
        return "redirect:/measurement";
    }
}



