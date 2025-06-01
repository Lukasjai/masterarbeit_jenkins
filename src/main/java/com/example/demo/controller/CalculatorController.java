package com.example.demo.controller;

import com.example.demo.service.CalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/Calculator")
    public String calculatorPage() {
        return "calculator";
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam int a, @RequestParam int b, Model model) {
        int result = calculatorService.add(a, b);
        model.addAttribute("result", result);
        return "calculator";
    }
}
