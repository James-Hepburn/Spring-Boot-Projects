package com.example.weatherapi.controller;

import com.example.weatherapi.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WeatherController {
    private WeatherService weatherService;

    public WeatherController (WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home () {
        return "index";
    }

    @PostMapping("/weather")
    public String getWeather (@RequestParam String location, Model model) {
        Map <String, Object> data = this.weatherService.getWeather (location);
        Map <String, Object> summary = this.weatherService.getSummary (data);

        model.addAttribute ("weatherData", summary);
        return "index";
    }
}
