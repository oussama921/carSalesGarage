package com.carSalesGarage.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carSalesGarage.model.Car;
import com.carSalesGarage.model.FuelType;

public interface CarRepo extends JpaRepository<Car,Long> {
	 @Query("SELECT c FROM Car c WHERE (:fuelType IS NULL OR c.fuelType = :fuelType) AND (:maxPrice IS NULL OR c.price <= :maxPrice)")
	    List<Car> findByFuelTypeAndMaxPrice(@Param("fuelType") FuelType fuelType, @Param("maxPrice") Double maxPrice);

	  @Query("SELECT DISTINCT c.make FROM Car c")
	    List<String> findDistinctMakes();
}
