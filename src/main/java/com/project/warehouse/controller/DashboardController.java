package com.project.warehouse.controller;

import com.project.warehouse.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    NotificationService notificationService;


    @GetMapping(path = "/most-sold")
    public String getMostSold(Model model){
        model.addAttribute("current", "dashboard");
        return "dashboard/most_sold";
    }
    @GetMapping(path = "/notifications")
    public String getNotificationPage(Model model, HttpServletRequest req){
        model.addAttribute("expire_date", notificationService.getNotificationsCount(req));
        return "dashboard/notifications";
    }

    @GetMapping("/context")
    public String context(Model model, HttpServletRequest req){
        model.addAttribute("notifications_count",notificationService.getNotificationsCount(req));
        return "context";
    }
}
