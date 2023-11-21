package com.carSalesGarage.service.files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    public void init();
    public void addCarPicture(MultipartFile file , String url);

}
