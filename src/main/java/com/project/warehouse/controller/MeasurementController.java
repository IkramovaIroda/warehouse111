package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.MeasurementDto;
import com.project.warehouse.entity.Measurement;
import com.project.warehouse.repository.MeasurementRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/data/measurement")
public class MeasurementController {

    final
    MeasurementService measurementService;
    final
    MeasurementRepository measurementRepository;
    final AuthService authService;

    @GetMapping
    public String getMeasurementPage(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("list", measurementRepository.findAllByActiveTrue());
        return "data/measurement";
    }

    @PostMapping
    public String saveMeasurement(@ModelAttribute Measurement measurement, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        measurementService.add(measurement);
        return "redirect:/data/measurement";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        Measurement measurement = byId.get();
        measurement.setActive(false);
        measurementRepository.save(measurement);
        return "redirect:/measurement";
    }

    @PostMapping("/edit/{id}")
    public String editMeasurement(@PathVariable Long id, @ModelAttribute MeasurementDto measurementDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        ApiResponse response = measurementService.edit(id, measurementDto);
        System.out.println(response);
        return "redirect:/data/measurement";
    }
}



