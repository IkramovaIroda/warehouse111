package com.project.warehouse.service;

import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.repository.InputProductRepository;
import com.project.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputProductRepository inputProductRepository;

    public int getExpirePeriod(HttpServletRequest req){
        if(req.getCookies()!=null){
            for (Cookie cookie : req.getCookies()) {
                if(cookie.getName().equals("product_expire_date")){
                    try {
                        return Integer.parseInt(cookie.getName());
                    }catch (Exception ignore){}
                }
            }
        }

        return 3;
    }


    public Integer getNotificationsCount(HttpServletRequest req){
        int finalExpire_period = getExpirePeriod(req);
        return inputProductRepository.findAll().stream()
                .filter(inputProduct -> checkProduct(inputProduct, finalExpire_period)).toList().size();
    }

    public boolean checkProduct(InputProduct inputProduct, int expire_period){
        if(LocalDate.now(ZoneId.of("Tashkent")).isBefore(inputProduct.getExpireDate())){
            return true;
        }
        Period period = Period.between(LocalDate.now(ZoneId.of("Tashkent")), inputProduct.getExpireDate());
        return period.getDays() < expire_period;
    }
}
