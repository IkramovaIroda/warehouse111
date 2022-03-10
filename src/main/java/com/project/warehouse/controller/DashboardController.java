package com.project.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping(path = "/most-sold")
    public String getMostSold(Model model){
        model.addAttribute("current", "dashboard");
        return "dashboard/most_sold";
    }
    @GetMapping(path = "/notifications")
    public String getNotificationPage(Model model, HttpServletRequest req){
        for (Cookie cookie : req.getCookies()) {
            if(cookie.getName().equals("product_expire_period")){
                model.addAttribute("expire_date", Integer.parseInt(cookie.getValue()));
            }
        }
        model.addAttribute("current", "dashboard");
        return "dashboard/notifications";
    }

    @GetMapping("/context")
    public String context(Model model){
        return "context";
    }
}
