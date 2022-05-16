package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.SupplierDto;
import com.project.warehouse.entity.Supplier;
import com.project.warehouse.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    final SupplierRepository supplierRepository;

    public void add(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDto.getName());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplierRepository.save(supplier);
    }

    public ApiResponse edit(SupplierDto supplierDto, Long id) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Supplier supplier = byId.get();
        supplier.setName(supplierDto.getName());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplierRepository.save(supplier);
        return null;
    }

    public void delete(Long id) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        byId.get().setActive(false);
        supplierRepository.save(byId.get());
    }

}
