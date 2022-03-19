package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.CurrencyDto;
import com.project.warehouse.dto.MeasurementDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.entity.Measurement;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.MeasurementRepository;
import com.project.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    public ApiResponse add(Measurement measurement) {
        Measurement save = measurementRepository.save(measurement);
        return new ApiResponse("Saved", true, save);
    }
    public ApiResponse edit(Long id, MeasurementDto measurementDto) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        Measurement measurement = optionalMeasurement.get();


        measurement.setName(measurementDto.getName());

        measurementRepository.save(measurement);
        return new ApiResponse("Updated!", true);
    }
}
