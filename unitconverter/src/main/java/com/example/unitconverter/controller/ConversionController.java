package com.example.unitconverter.controller;

import com.example.unitconverter.service.UnitConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConversionController {
    private UnitConversionService conversionService;

    public ConversionController (UnitConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping ("/convert")
    public String convert (@RequestParam double amount, @RequestParam String from, @RequestParam String to, RedirectAttributes redirect) {
        double result = this.conversionService.convert (amount, from, to);

        redirect.addAttribute ("amount", amount);
        redirect.addAttribute ("from", from);
        redirect.addAttribute ("to", to);
        redirect.addAttribute ("result", result);

        return "redirect:/";
    }
}
