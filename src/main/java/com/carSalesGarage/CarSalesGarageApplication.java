package com.carSalesGarage;

import com.carSalesGarage.service.FileStorageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.Resource;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Car Sales Garage",
				version="1.0.0",
				description = "This is a test project ",
				contact =@Contact(
						name="Oussama Bedoui",
						email = "bedouioussama@gmail.com"
						)
				)
		
		)
public class CarSalesGarageApplication {

    @Resource
	FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(CarSalesGarageApplication.class, args);
	}
	
	public void run(String... arg) throws Exception {
        fileStorageService.init();
    }

}
