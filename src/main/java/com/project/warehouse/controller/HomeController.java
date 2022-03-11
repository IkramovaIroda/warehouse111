package com.project.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class HomeController {
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
