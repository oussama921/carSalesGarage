package com.carSalesGarage.dto;

// imports...

import com.carSalesGarage.model.FuelType;
import com.carSalesGarage.model.TransmissionType;

import java.time.LocalDate;

public class CarDTO {


    Long id;
    private String make;
    private String model;
    private LocalDate registrationDate;
    private double price;
    private FuelType fuelType;
    private double mileage;
    private TransmissionType transmission;
    private String picture;

    // Getters and setters...

}
