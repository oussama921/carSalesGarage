package com.carSalesGarage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carSalesGarage.model.Car;
import com.carSalesGarage.model.FuelType;
import com.carSalesGarage.repo.CarRepo;

@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private FileStorageService fileStorageService;

    public Car addCar(Car car) {
        return carRepo.save(car);
    }

    public List<Car> getAllCars(FuelType fuelType, Double maxPrice) {
        return carRepo.findByFuelTypeAndMaxPrice(fuelType, maxPrice);
    }

    public List<String> getAllMakes() {
        return carRepo.findDistinctMakes();
    }

    public Car getCar(Long id) {
        return carRepo.findById(id).orElse(null);
    }


    public Car updateCarPicture(Long id, String fileName) {
        System.out.println("iddd"+id);
        Car car = carRepo.findById(id).orElse(null);
        assert car != null;
        car.setPicture(fileName);
        return carRepo.save(car);
    }

}
