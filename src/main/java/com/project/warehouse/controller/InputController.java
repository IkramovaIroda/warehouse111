package com.project.warehouse.controller;

import com.project.warehouse.dto.InputDto;
import com.project.warehouse.entity.Input;
import com.project.warehouse.entity.InputProduct;
import com.project.warehouse.repository.InputProductRepository;
import com.project.warehouse.repository.InputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.warehouse.service.InputService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/input")
public class InputController {

    @Autowired
    InputRepository inputRepository;
    
    @Autowired
    InputService inputService;

    @Autowired
    InputProductRepository inputProductRepository;

    static Input input = new Input();

    @GetMapping
    public String getInputs(Model model){
        model.addAttribute("inputList", inputRepository.findAll());
        return "input/input";
    }
    @GetMapping("/getInput/{id}")
    public String getOneInput(@PathVariable Long id, Model model){
        Optional<Input> byId = inputRepository.findById(id);
         input = byId.get();
        model.addAttribute("input", input);
        return "input/input";
    }

    @GetMapping("/getInput/getInputProducts")
    public String getInputProducts(Model model){
        List<InputProduct> byInput = inputProductRepository.findByInput(input);
        model.addAttribute("inputProducts", byInput);
        return "input/getProducts";
    }

    @GetMapping("/addInput")
    public String add(){
        return "input/addInput";
    }

    @PostMapping("/addInput")
    public String saveInput(@ModelAttribute InputDto inputDto){
        inputService.save(inputDto);
        return "redirect:/input";
    }

    @GetMapping("/getInput//delete/{id}")
    public String delete(@PathVariable Long id){
        Optional<Input> byId = inputRepository.findById(id);
        Input input = byId.get();
        input.setActive(false);
        return "redirect:/input";
    }

    @GetMapping("/getInput/editInput/{id}")
    public String edit(Model model, @PathVariable Long id){
        model.addAttribute("replaceableInput", inputRepository.findById(id).get());
        return "edit";
    }

    @PostMapping("/editInputSave/{id}")
    public String editSave(@PathVariable Long id, @ModelAttribute InputDto inputDto){
        inputService.edit(id, inputDto);
        return "redirect:/input/input";
    }

    @GetMapping("/getInput/getInputProducts/editInputProducts/{id}")
    public String editInputProducts(@PathVariable Long id, Model model){
          model.addAttribute("products", inputProductRepository.findById(id).get());
          return "input/editInputProducts";
    }
    @GetMapping("/getInput/getInputProducts/deleteInputProducts/{id}")
    public String deleteInputProducts(@PathVariable Long id){
        inputProductRepository.deleteById(id);
        return "/getInput/getInputProducts";

    }
}
