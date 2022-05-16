package com.project.warehouse.controller;

import com.project.warehouse.dto.LoginDto;
import com.project.warehouse.entity.User;
import com.project.warehouse.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    final AuthService authService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest req, Model model) {
        String return_url = req.getParameter("return_url");
        if (authService.checkToken(authService.getToken(req.getCookies()))) {
            if (return_url != null && return_url.startsWith("/")) {
                return "redirect:" + return_url;
            }
            return "redirect:/dashboard/most-sold";
        }
        model.addAttribute("return_url",
                return_url != null ? return_url : "/dashboard/most-sold");
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse res, @ModelAttribute LoginDto loginDto, Model model) {
        String return_url = req.getParameter("return_url");
        User user = authService.getUser(loginDto.getLogin(), loginDto.getPassword());
        if (user == null) {
            return "redirect:/auth/login?error&return_url=" + (return_url != null ? return_url : "/dashboard/most-sold");
        }
        res.addCookie(authService.generateToken(user));
        if (return_url != null && return_url.startsWith("/")) {
            return "redirect:" + req.getParameter("return_url");
        }
        return "redirect:/dashboard/most-sold";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse res) {
        authService.logout(res);
        return "redirect:/auth/login";
    }
}
