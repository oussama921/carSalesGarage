package com.carSalesGarage.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.carSalesGarage.service.files.FileStorageServiceImpl;
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
    private FileStorageServiceImpl fileStorageService;

    @PostMapping(value = "/car")
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        // Assuming that the date format is "dd-MM-yyyy"
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String registrationDateStr = car.getRegistrationDate();
        if (registrationDateStr == null) {
            return new ResponseEntity<>("Registration date is required.", HttpStatus.BAD_REQUEST);
        }

        try {
            LocalDate registrationDate = LocalDate.parse(registrationDateStr, DATE_FORMATTER);
            // Assuming 2015-01-01 is the cutoff date
            LocalDate cutoffDate = LocalDate.of(2015, 1, 1);

            if (registrationDate.isAfter(cutoffDate)){

                System.out.println(car.getRegistrationDate());
                Car carObj = carService.addCar(car);

                System.out.println(carObj.toString());
                return new ResponseEntity<Car>(carObj, HttpStatus.OK);
            }else {
                // Handle the case where the car is not registered after 2015
                return new ResponseEntity<>("The car should be registered after 2015 .",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCarsByFuelTypeAndMaxPrice(@RequestParam(value = "fuelType", required = false) FuelType fuelType,
                                                  @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        try{


            List<Car> filtredCars = carService.getAllCars(fuelType, maxPrice);

            return new ResponseEntity<List<Car>>(filtredCars, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/makes")
    public ResponseEntity<?> getAllMakes() {
        try{
            List<String> MakesList = carService.getAllMakes();

            return new ResponseEntity<List<String>>(MakesList, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/car/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Car> updateCarById(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        System.out.println("id"+id);
        System.out.println("file"+file);
        try{

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
            Date date = new Date(System.currentTimeMillis());

            String url = formatter.format(date)+" " + file.getOriginalFilename();
            fileStorageService.addCarPicture(file, url);
            System.out.println("heeeere");

            Car car = carService.updateCarPicture(id, url);
            System.out.println("car"+file);

            return new ResponseEntity<Car>(car, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
