package com.yordanos.sms.controller;

import com.yordanos.sms.exception.ResourceNotFoundException;
import com.yordanos.sms.model.Image;
import com.yordanos.sms.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/view/id/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable Long id) throws SQLException {
        try {
            Image image = imageService.getImageById(id);
            if (image != null) {
                ByteArrayResource resource = new ByteArrayResource(image.getImageFile().getBytes(1, (int) image.getImageFile().length()));
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new ResourceNotFoundException("Image with id " + id + " not found");
            }
        } catch (ResourceNotFoundException | SQLException e) {
            return ResponseEntity.status(NOT_FOUND).body(null);
        }
    }
}
