package com.carSalesGarage.service.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	@Value("${storage}")
    private String storage;
    public FileStorageServiceImpl() {
    }

    @Override
    public void init() {
        try {
            String folderPath = "src/main/resources/static/storage01"; // Change the path as needed

            File folder = new File(folderPath);

            if (!folder.exists()) {
                boolean folderCreated = folder.mkdirs();

                if (folderCreated) {
                    System.out.println("Static folder created successfully.");
                } else {
                    System.err.println("Failed to create static folder.");
                }
            } else {
                System.out.println("Static folder already exists.");
            }
            Files.createDirectory(Paths.get(storage));

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
	@Override
    public void addCarPicture(MultipartFile file , String url) {
        try {
            Files.copy(file.getInputStream(),Paths.get(storage).resolve(url));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
