package com.project.warehouse.controller;

import com.project.warehouse.repository.CurrencyRepository;
import com.project.warehouse.repository.ProductRepository;
import com.project.warehouse.repository.SupplierRepository;
import com.project.warehouse.repository.WarehouseRepository;
import com.project.warehouse.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    final
    ProductRepository productRepository;
    final
    SupplierRepository supplierRepository;
    final
    WarehouseRepository warehouseRepository;
    final
    CurrencyRepository currencyRepository;
    final AuthService authService;

    @GetMapping
    public String homePage(HttpServletRequest req){
        if (authService.checkToken(authService.getToken(req.getCookies()))) {
            return "redirect:/dashboard/most-sold";
        }
        return "redirect:/auth/login";
    }

    @GetMapping(path = "/favicon.ico")
    public void favicon(HttpServletResponse response){
        response.setStatus(302);
        response.setHeader("Location", "/assets/favicon.ico");
    }
}
