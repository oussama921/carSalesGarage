package com.carSalesGarage.service;

import java.util.List;
import java.util.stream.Collectors;

import com.carSalesGarage.service.files.FileStorageService;
import com.carSalesGarage.service.files.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.carSalesGarage.model.Car;
import com.carSalesGarage.model.FuelType;
import com.carSalesGarage.repo.CarRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CarService {

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

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
