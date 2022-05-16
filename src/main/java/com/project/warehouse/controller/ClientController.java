package com.project.warehouse.controller;

import com.project.warehouse.dto.ClientDto;
import com.project.warehouse.entity.Client;
import com.project.warehouse.repository.ClientRepository;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/client")
public class ClientController {

    final
    ClientService clientService;
    final
    ClientRepository clientRepository;
    final AuthService authService;

    @GetMapping
    public String getClientPage(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("list", clientRepository.findAllByActiveTrue());
        return "users/client";
    }

    @PostMapping
    public String saveClient(@ModelAttribute ClientDto clientDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        clientService.add(clientDto);
        return "redirect:/users/client";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Client> byId = clientRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        clientService.delete(id);
        return "redirect:/users/client";
    }

    @PostMapping("/edit/{id}")
    public String editClient(@PathVariable Long id, @ModelAttribute ClientDto clientDto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        clientService.edit(clientDto, id);
        return "redirect:/users/client";
    }
}
