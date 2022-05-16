package com.project.warehouse.controller;

import com.project.warehouse.dto.ChosenProductsDto;
import com.project.warehouse.dto.OutputDto;
import com.project.warehouse.entity.Currency;
import com.project.warehouse.entity.Output;
import com.project.warehouse.entity.OutputProduct;
import com.project.warehouse.entity.Warehouse;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.AuthService;
import com.project.warehouse.service.OutputService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/output")
@RequiredArgsConstructor
public class OutputController {

    final
    OutputRepository outputRepository;
    final
    CurrencyRepository currencyRepository;
    final
    WarehouseRepository warehouseRepository;
    final
    ClientRepository clientRepository;
    final
    OutputService outputService;
    final
    OutputProductRepository outputProductRepository;
    final
    ProductRepository productRepository;
    final AuthService authService;
    final InputProductRepository inputProductRepository;
//    , HttpServletRequest req, HttpServletResponse res
//    if (authService.deleteTokenIf(req, res)) {return "secured-page";}

    @GetMapping("/all")
    public String getAll(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("outputList", outputRepository.findByActiveTrue());
        return "output/all";
    }

    @GetMapping("/addOutput")
    public String addOutput(Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("clientList", clientRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now().toString());
        return "output/output-add";
    }

    @PostMapping("/addOutput/choose-products")
    public String add(@ModelAttribute OutputDto dto, Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        model.addAttribute("output", dto);
        List<Warehouse> warehouses = warehouseRepository.findAllByActiveTrue();
        List<Currency> currencies = currencyRepository.findAllByActiveTrue();
        warehouses.removeIf(warehouse -> !Objects.equals(warehouse.getId(), dto.getWarehouseId()));
        currencies.removeIf(currency -> !Objects.equals(currency.getId(), dto.getCurrencyId()));
        if (warehouses.size() == 0 || currencies.size() == 0) return "error/404";
        model.addAttribute("warehouse", warehouses.get(0));
        model.addAttribute("currency", currencies.get(0));
        model.addAttribute("client", dto.getClientId());
        model.addAttribute("clientList", clientRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now().toString());
        model.addAttribute("products", inputProductRepository
                .findAllByInput_ActiveTrueAndInput_Warehouse_idAndInput_Currency_idAndAmountNot(
                        dto.getWarehouseId(), dto.getCurrencyId(), 0));
        return "output/output-choose-product";
    }

    @PostMapping("/addOutput/products")
    public String chosenProducts(@ModelAttribute ChosenProductsDto chosenProductsDto, Model model, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        chosenProductsDto.getProductList().remove(null);
        model.addAttribute("inputProducts",
                inputProductRepository.findAllById(chosenProductsDto.getProductList()));
        List<Warehouse> warehouses = warehouseRepository.findAllByActiveTrue();
        List<Currency> currencies = currencyRepository.findAllByActiveTrue();
        warehouses.removeIf(warehouse -> !Objects.equals(warehouse.getId(), chosenProductsDto.getWarehouseId()));
        currencies.removeIf(currency -> !Objects.equals(currency.getId(), chosenProductsDto.getCurrencyId()));
        if (warehouses.size() == 0 || currencies.size() == 0) return "error/404";
        model.addAttribute("date", chosenProductsDto.getDate());
        model.addAttribute("factureNumber", chosenProductsDto.getFactureNumber());
        model.addAttribute("warehouse", warehouses.get(0));
        model.addAttribute("currency", currencies.get(0));
        model.addAttribute("client", chosenProductsDto.getClientId());
        model.addAttribute("clientList", clientRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now().toString());
        return "output/output-product";
    }

    @PostMapping("/addOutputProduct")
    public String saveOutputProduct(OutputDto dto, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        outputService.saveOutput(dto);
        return "redirect:/output/all";
    }

    @GetMapping("/getOutput/{id}")
    public String getOutputProducts(@PathVariable Long id, Model model) {
        Optional<Output> byId = outputRepository.findById(id);
        if (byId.isEmpty()) {
            return "error/404";
        }
        model.addAttribute("output", byId.get());
        List<OutputProduct> outputProducts =
                outputProductRepository.findAllByOutput_IdAndOutputActiveTrue(id);
        model.addAttribute("outputProducts", outputProducts);
        return "output/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteOutput(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res) {
        if (authService.deleteTokenIf(req, res)) {
            return "secured-page";
        }
        Optional<Output> byId = outputRepository.findById(id);
        if (byId.isEmpty()) return "error/404";
        outputService.delete(id);
        return "redirect:/output/all";
    }


}
