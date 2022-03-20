package com.project.warehouse.controller;

import com.project.warehouse.dto.ApiResponse;
import com.project.warehouse.dto.ClientDto;
import com.project.warehouse.entity.Client;
import com.project.warehouse.repository.ClientRepository;
//import com.project.warehouse.service.AuthService;
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
@RequestMapping("/data/client")
public class ClientController {

    final
    ClientService clientService;
    final
    ClientRepository clientRepository;
//    final AuthService authService;

    @GetMapping
    public String getClientPage(Model model, HttpServletRequest req, HttpServletResponse res) {
//        if (authService.deleteTokenIf(req, res)) {
//            return "redirect:/auth/login?return_url="+req.getServletPath();
//        }
        model.addAttribute("list", clientRepository.findAllByActiveTrue());
        return "data/client";
    }

    @PostMapping
    public String saveClient(@ModelAttribute Client client) {
        clientService.add(client);
        return "redirect:/data/client";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Client> byId = clientRepository.findById(id);
        if (byId.isEmpty()) return "404";
        Client client = byId.get();
        clientRepository.save(client);
        return "redirect:/client";
    }

    @PostMapping("/edit/{id}")
    public String editClient(@PathVariable Long id, @ModelAttribute ClientDto clientDto) {
        ApiResponse response = clientService.edit(clientDto,id);
        System.out.println(response);
        return "redirect:/data/client";
    }
}
