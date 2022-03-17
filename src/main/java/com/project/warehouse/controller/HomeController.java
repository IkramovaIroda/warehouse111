package com.project.warehouse.controller;

import com.project.warehouse.repository.CurrencyRepository;
import com.project.warehouse.repository.ProductRepository;
import com.project.warehouse.repository.SupplierRepository;
import com.project.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

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

    @GetMapping
    public String redirect(){
        return "redirect:/dashboard/most-sold";
    }

    @GetMapping(path = "/favicon.ico")
    public void favicon(HttpServletResponse response){
        response.setStatus(302);
        response.setHeader("Location", "/assets/favicon.ico");
    }
}
