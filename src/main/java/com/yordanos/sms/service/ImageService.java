package com.yordanos.sms.service;

import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Image;
import com.yordanos.sms.repository.ImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepo imageRepo;

    public Image getImageById(Long id) throws ResourceNotFoundException {
        return imageRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Image with id " + id + " not found"));
    }

    public Image saveImage(MultipartFile file) {
        Image image = new Image();
        try {
            image.setFilename(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImageFile(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return imageRepo.save(image);
    }

    public void deleteImage(Long id) throws ResourceNotFoundException {
        imageRepo.findById(id)
                .ifPresentOrElse(imageRepo::delete, ()-> {
                    throw new ResourceNotFoundException("Image with id " + id + " not found");
                });
    }

    public Image updateImage(MultipartFile file, Long id) {
        Image image = getImageById(id);
        try {
            image.setFilename(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImageFile(new SerialBlob(file.getBytes()));
            return imageRepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
