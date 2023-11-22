package com.carSalesGarage.service;

import com.carSalesGarage.controller.CarController;
import com.carSalesGarage.model.Car;
import com.carSalesGarage.model.FuelType;
import com.carSalesGarage.service.files.FileStorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarService carService;

    @Mock
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCar_ValidRegistrationDate() {
        // Arrange
        Car car = new Car();
        car.setRegistrationDate("01-01-2022");

        when(carService.addCar(any())).thenReturn(car);

        // Act
        ResponseEntity<?> response = carController.addCar(car);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(car, response.getBody());
    }

    @Test
    void testAddCar_InvalidRegistrationDate() {
        // Arrange
        Car car = new Car();
        car.setRegistrationDate("01-01-2010");

        // Act
        ResponseEntity<?> response = carController.addCar(car);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetCarsByFuelTypeAndMaxPrice() {
        // Arrange
        FuelType fuelType = FuelType.DIESEL;
        double maxPrice = 50000.0;
        List<Car> cars = Collections.singletonList(new Car());

        when(carService.getAllCars(fuelType, maxPrice)).thenReturn(cars);

        // Act
        ResponseEntity<List<Car>> response = carController.getCarsByFuelTypeAndMaxPrice(fuelType, maxPrice);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cars, response.getBody());
    }

    @Test
    void testGetAllMakes() {
        // Arrange
        List<String> makes = Arrays.asList("Toyota", "Honda", "BMW");

        when(carService.getAllMakes()).thenReturn(makes);

        // Act
        ResponseEntity<?> response = carController.getAllMakes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(makes, response.getBody());
    }

    @Test
    void testUpdateCarById() throws Exception {
        // Arrange
        Long id = 1L;
        MultipartFile file = mock(MultipartFile.class);

        // Mock the behavior of the file
        when(file.getOriginalFilename()).thenReturn("test.jpg");

        // Mock the behavior of the fileStorageService
        doNothing().when(fileStorageService).addCarPicture(any(), any());

        // Mock the behavior of the carService
        Car updatedCar = new Car();
        updatedCar.setId(id);
        when(carService.updateCarPicture(id, "yyyyMMdd-HHmmss test.jpg")).thenReturn(updatedCar);

        // Act
        ResponseEntity<Car> response = carController.updateCarById(id, file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedCar, response.getBody());

        // Verify interactions
        verify(fileStorageService, times(1)).addCarPicture(eq(file), anyString());
        verify(carService, times(1)).updateCarPicture(eq(id), eq("yyyyMMdd-HHmmss test.jpg"));
    }

}