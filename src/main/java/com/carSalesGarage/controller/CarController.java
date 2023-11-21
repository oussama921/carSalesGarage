package com.carSalesGarage.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.carSalesGarage.model.Car;
import com.carSalesGarage.model.FuelType;
import com.carSalesGarage.repo.CarRepo;
import com.carSalesGarage.service.CarService;
import com.carSalesGarage.service.files.FileStorageService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.util.StringUtils;

@RestController
public class CarController {

    @Autowired
    private CarService carService;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/car")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car carObj = carService.addCar(car);
            System.out.println(carObj.toString());
        return new ResponseEntity<Car>(carObj, HttpStatus.OK);
    }

    @GetMapping("/cars")
    public List<Car> getCarsByFuelTypeAndMaxPrice(@RequestParam(value = "fuelType", required = false) FuelType fuelType,
                                                  @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        List<Car> filtredCars = carService.getAllCars(fuelType, maxPrice);

        return filtredCars;

    }

    @GetMapping("/makes")
    public List<String> getAllMakes() {
        List<String> MakesList = carService.getAllMakes();

        return MakesList;
    }

    @PatchMapping(value = "/car/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Car> updateCarById(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date(System.currentTimeMillis());

        String url = formatter.format(date)+" " + file.getOriginalFilename();
        fileStorageService.addCarPicture(file, url);

        Car car = carService.updateCarPicture(id, url);


        return new ResponseEntity<Car>(car, HttpStatus.OK);

    }


}
