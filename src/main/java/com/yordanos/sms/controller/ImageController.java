package com.yordanos.sms.controller;

import com.yordanos.sms.model.Image;
import com.yordanos.sms.response.ApiResponse;
import com.yordanos.sms.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.FOUND;


@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse> getImageById(@PathVariable Long id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok(new ApiResponse("Found", image));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteImageById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse("Deleted", null));
    }
}
