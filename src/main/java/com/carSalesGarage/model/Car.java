package com.carSalesGarage.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Car")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	private String make;
	private String model;
	private String registrationDate;
	private Double price;
	private FuelType fuelType;
	private String mileage;
	private TransmissionType transmission;

    private String picture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public void setTransmission(TransmissionType transmission) {
		this.transmission = transmission;
	}

	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public void setMake(String newMake) {
		make=newMake;
	}


	public String getMake() {
		return make;
	}
	public void setModel(String newModel) {
		model=newModel;
	}


	public String getModel() {
		return model;
	}


	public FuelType getFuelType() {
		return fuelType;
	}


	public TransmissionType getTransmission() {
		return transmission;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}

	
}
