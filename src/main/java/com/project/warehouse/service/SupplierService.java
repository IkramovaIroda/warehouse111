package com.project.warehouse.service;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.SupplierDto;
import com.project.warehouse.dto.SupplierDto;
import com.project.warehouse.entity.Supplier;
import com.project.warehouse.entity.Supplier;
import com.project.warehouse.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    final SupplierRepository supplierRepository;

    public void add(Supplier supplierDto){
        if(supplierDto.getName().equalsIgnoreCase("#NULL"))return ;
        Supplier supplier=new Supplier();
        if(!supplierDto.getId().equals("#null")){
            Optional<Supplier> byId = supplierRepository.findById(supplierDto.getId());
            if(byId.isEmpty()){
                return;
            }
        }
        supplier.setName(supplierDto.getName());
        supplierRepository.save(supplier);
    }

    public ApiResponse edit(SupplierDto supplierDto, Long id){
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        Supplier supplier = byId.get();
        if (supplierDto.getId().equals("#null")) {
            supplier.setId(null);
        }else {
            Optional<Supplier> byId1 = supplierRepository.findById(supplierDto.getId());
            if (byId1.isEmpty()) {
                return null;
            }
            supplier.setId(byId1.get().getId());
        }
        supplier.setName(supplierDto.getName());
        supplierRepository.save(supplier);
        return null;
    }

    public void delete(Long id) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        supplierRepository.save(byId.get());
    }

}
