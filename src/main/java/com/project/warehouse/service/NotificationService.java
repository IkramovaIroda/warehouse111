package com.project.warehouse.service;

import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.repository.InputProductRepository;
import com.project.warehouse.repository.ProductRepository;
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
public class NotificationService {
    final
    ProductRepository productRepository;
    final
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
    public void setExpirePeriod(HttpServletResponse res, int period){
        Cookie cookie=new Cookie("product_expire_date", String.valueOf(period));
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }

    public Integer getNotificationsCount(HttpServletRequest req){
        int finalExpire_period = getExpirePeriod(req);
        LocalDate minDate=LocalDate.now().plusDays(finalExpire_period);
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
