package com.project.warehouse.controller;

import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.entity.Output;
import com.project.warehouse.entity.OutputProduct;
import com.project.warehouse.entity.Product;
import com.project.warehouse.repository.*;
import com.project.warehouse.service.OutputService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.warehouse.dto.OutputDto;

import java.util.List;

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
    static Output output;
    final
    OutputProductRepository outputProductRepository;
    final
    ProductRepository productRepository;

    @GetMapping("/all")
    public String getAll(Model model){
        model.addAttribute("outputList", outputRepository.findByActiveTrue());
        return "output/getAllOutput";
    }

    @GetMapping("/addOutput")
    public String addOutput(Model model){
        model.addAttribute("wareHouseList",warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("clientList", clientRepository.findAll());
        return "output/addOutput";
    }

    @PostMapping("/addOutput")
    public String add (@ModelAttribute OutputDto dto){
         output = outputService.saveOutput(dto);
        return "redirect:/addOutputProduct";
    }

    @GetMapping("/addOutputProduct")
    public String addOutputProduct(Model model){
        List<InputProduct> inputProducts = outputService.addOutput(output);
        model.addAttribute("output", output);
        model.addAttribute("inputProductList", inputProducts);
        return "output/outputProduct-add";
    }
    @PostMapping("/addOutputProduct")
    public String saveOutputProduct(OutputDto dto){
        outputService.saveOutputProducts(dto, output);
        return "redirect:/all";
    }
    
    @GetMapping("/edit/{id}")
    public String editOutput(@PathVariable Long id, Model model){
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("warehoseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("clientList", clientRepository.findAll());
        return null;
    }

    @PostMapping("/edit/{id}")
    public String saveEditOutput(@PathVariable Long id, @ModelAttribute OutputDto outputDto){
        outputService.saveEditOutput(id, outputDto);
        return "redirect:/all";
    }
    @GetMapping("/getOutputProducts/editOutputProducts/{id}")
    public String editOutputproducts(@PathVariable Long id, Model model){
        Output output1 = outputRepository.findById(id).get();
        List<OutputProduct> outputProductList = outputProductRepository.findAllByOutput_Id(id);
        List <Product> products = productRepository.findAllByActiveTrue();
        model.addAttribute("outputProductList", outputProductList);
        model.addAttribute("output", output1);
        model.addAttribute("productList", products);
        return "output/outputProducts-add";
    }
    @PostMapping("/getOutputProducts/editOutputProducts/{id}")
    public String saveEditOutputProducts(@PathVariable Long id, @ModelAttribute OutputDto outputDto){
        outputService.saveEditOutputProducts(id, outputDto);
        return "redirect:/getOutputProducts/"+id;
    }

    @GetMapping("/getOutputProducts/{id}")
    public String getOutputProducts(@PathVariable Long id, Model model){
        List<OutputProduct> outputProducts = outputProductRepository.findAllByOutput_Id(id);
        model.addAttribute("outputProductsList", outputProducts);
        return "output/getOutputProducts";
    }

    @GetMapping("/delete/{id}")
    public String deleteOutput(@PathVariable Long id){
        Output output = outputRepository.findById(id).get();
        output.setActive(false);
        outputRepository.save(output);
        return "redirect:/output/all";
    }










}
