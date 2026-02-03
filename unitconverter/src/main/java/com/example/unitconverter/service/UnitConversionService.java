package com.example.unitconverter.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UnitConversionService {
    private Map <String, Double> toMillimeters = Map.of (
            "mm", 1.0,
            "cm", 10.0,
            "m", 1000.0,
            "km", 1_000_000.0,
            "in", 25.4,
            "ft", 304.8,
            "yd", 914.4,
            "mi", 1_609_344.0
    );

    private Map <String, Double> toCentimeters = Map.of (
            "mm", 0.1,
            "cm", 1.0,
            "m", 100.0,
            "km", 100_000.0,
            "in", 2.54,
            "ft", 30.48,
            "yd", 91.44,
            "mi", 160_934.4
    );

    private Map <String, Double> toMeters = Map.of (
            "mm", 0.001,
            "cm", 0.01,
            "m", 1.0,
            "km", 1000.0,
            "in", 0.0254,
            "ft", 0.3048,
            "yd", 0.9144,
            "mi", 1609.344
    );

    private Map <String, Double> toKilometers = Map.of (
            "mm", 0.000001,
            "cm", 0.00001,
            "m", 0.001,
            "km", 1.0,
            "in", 0.0000254,
            "ft", 0.0003048,
            "yd", 0.0009144,
            "mi", 1.609344
    );

    private Map <String, Double> toInches = Map.of (
            "mm", 0.0393701,
            "cm", 0.393701,
            "m", 39.3701,
            "km", 39_370.1,
            "in", 1.0,
            "ft", 12.0,
            "yd", 36.0,
            "mi", 63_360.0
    );

    private Map <String, Double> toFeet = Map.of (
            "mm", 0.00328084,
            "cm", 0.0328084,
            "m", 3.28084,
            "km", 3280.84,
            "in", 0.0833333,
            "ft", 1.0,
            "yd", 3.0,
            "mi", 5_280.0
    );

    private Map <String, Double> toYards = Map.of (
            "mm", 0.00109361,
            "cm", 0.0109361,
            "m", 1.09361,
            "km", 1093.61,
            "in", 0.0277778,
            "ft", 0.333333,
            "yd", 1.0,
            "mi", 1760.0
    );

    private Map <String, Double> toMiles = Map.of (
            "mm", 0.000000621371,
            "cm", 0.00000621371,
            "m", 0.000621371,
            "km", 0.621371,
            "in", 0.0000157828,
            "ft", 0.000189394,
            "yd", 0.000568182,
            "mi", 1.0
    );

    private Map <String, Double> toMilligrams = Map.of (
            "mg", 1.0,
            "g", 1000.0,
            "oz", 28_349.5,
            "lb", 453_592.37,
            "kg", 1_000_000.0
    );

    private Map <String, Double> toGrams = Map.of (
            "mg", 0.001,
            "g", 1.0,
            "oz", 28.3495,
            "lb", 453.59237,
            "kg", 1000.0
    );

    private Map <String, Double> toOunces = Map.of (
            "mg", 0.000035274,
            "g", 0.035274,
            "oz", 1.0,
            "lb", 16.0,
            "kg", 35.274
    );

    private Map <String, Double> toPounds = Map.of (
            "mg", 0.00000220462,
            "g", 0.00220462,
            "oz", 0.0625,
            "lb", 1.0,
            "kg", 2.20462
    );

    private Map <String, Double> toKilograms = Map.of (
            "mg", 0.000001,
            "g", 0.001,
            "oz", 0.0283495,
            "lb", 0.45359237,
            "kg", 1.0
    );

    public double toKelvin (double amount, String from) {
        if (from.equals ("°C")) {
            return amount + 273.15;
        } else if (from.equals ("°F")) {
            return (amount - 32) * 5 / 9 + 273.15;
        }

        return amount;
    }

    public double toCelsius (double amount, String from) {
        if (from.equals ("K")) {
            return amount - 273.15;
        } else if (from.equals ("°F")) {
            return (amount - 32) * 5 / 9;
        }

        return amount;
    }

    public double toFahrenheit (double amount, String from) {
        if (from.equals ("K")) {
            return (amount - 273.15) * 9 / 5 + 32;
        } else if (from.equals ("°C")) {
            return amount * 9 / 5 + 32;
        }

        return amount;
    }

    public double convert (double amount, String from, String to) {
        if (to.equals ("mm")) {
            return toMillimeters.get (from) * amount;
        } else if (to.equals ("cm")) {
            return toCentimeters.get (from) * amount;
        } else if (to.equals ("m")) {
            return toMeters.get (from) * amount;
        } else if (to.equals ("km")) {
            return toKilometers.get (from) * amount;
        } else if (to.equals ("in")) {
            return toInches.get (from) * amount;
        } else if (to.equals ("ft")) {
            return toFeet.get (from) * amount;
        } else if (to.equals ("yd")) {
            return toYards.get (from) * amount;
        } else if (to.equals ("mi")) {
            return toMiles.get (from) * amount;
        } else if (to.equals("mg")) {
            return toMilligrams.get (from) * amount;
        } else if (to.equals("g")) {
            return toGrams.get (from) * amount;
        } else if (to.equals("oz")) {
            return toOunces.get (from) * amount;
        } else if (to.equals("lb")) {
            return toPounds.get (from) * amount;
        } else if (to.equals("kg")) {
            return toKilograms.get (from) * amount;
        } else if (to.equals ("K")) {
            return toKelvin (amount, from);
        } else if (to.equals ("°C")) {
            return toCelsius (amount, from);
        } else if (to.equals ("°F")) {
            return toFahrenheit (amount, from);
        }

        return 0;
    }
}
