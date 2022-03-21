package com.project.warehouse.service;

import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.repository.InputProductRepository;
import com.project.warehouse.repository.ProductRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        return inputProductRepository.countByExpireDateBefore(minDate);
    }

    public boolean checkProduct(InputProduct inputProduct, int expire_period){
        if(LocalDate.now(ZoneId.of("Tashkent")).isBefore(inputProduct.getExpireDate())){
            return true;
        }
        Period period = Period.between(LocalDate.now(ZoneId.of("Tashkent")), inputProduct.getExpireDate());
        return period.getDays() < expire_period;
    }
}
