package com.project.warehouse.service;

import com.project.warehouse.repository.InputProductRepository;
import com.project.warehouse.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Data
public class NotificationService {
    final
    ProductRepository productRepository;
    final
    InputProductRepository inputProductRepository;
    private int expire_period=3;

    public Integer getNotificationsCount(){
        LocalDate minDate=LocalDate.now().plusDays(expire_period);
        return inputProductRepository.countByExpireDateBeforeAndInput_ActiveTrueAndAmountNot(minDate, 0D);
    }
}
