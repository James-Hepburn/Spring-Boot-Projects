package com.example.weatherapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate ();

    public Map <String, Object> getSummary (Map <String, Object> data) {
        Map <String, Object> summary = new HashMap <>();
        summary.put ("location", data.get ("resolvedAddress"));
        List <Map<String, Object>> days = (List <Map <String, Object>>) data.get ("days");

        if (days != null && !days.isEmpty ()) {
            Map <String, Object> today = days.get (0);

            summary.put ("date", today.get ("datetime"));
            summary.put ("temp", today.get ("temp"));
            summary.put ("tempMax", today.get ("tempmax"));
            summary.put ("tempMin", today.get ("tempmin"));
            summary.put ("feelsLike", today.get ("feelslike"));
            summary.put ("conditions", today.get ("conditions"));
            summary.put ("description", today.get ("description"));
            summary.put ("humidity", today.get ("humidity"));
            summary.put ("precipitation", today.get ("precip"));
            summary.put ("windSpeed", today.get ("windspeed"));
            summary.put ("sunrise", today.get ("sunrise"));
            summary.put ("sunset", today.get ("sunset"));
        }

        return summary;
    }

    public Map <String, Object> getWeather (String location) {
        String url = String.format (
                "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s?unitGroup=metric&key=%s",
                location,
                this.apiKey
        );

        try {
            return restTemplate.getForObject (url, Map.class);
        } catch (Exception e) {
            return Map.of ("error", "Unexpected error occurred");
        }

    }
}