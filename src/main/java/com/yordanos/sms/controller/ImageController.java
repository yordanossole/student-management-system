package com.yordanos.sms.controller;

import com.yordanos.sms.model.Image;
import com.yordanos.sms.response.ApiResponse;
import com.yordanos.sms.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok(new ApiResponse("Found", image.getId()));
    }
}
