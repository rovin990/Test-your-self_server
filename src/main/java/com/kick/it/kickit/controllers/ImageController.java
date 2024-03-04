package com.kick.it.kickit.controllers;

import com.kick.it.kickit.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        System.out.println("Upload image controller");
        ResponseEntity response=null;
        try{
            String uploadImage= imageService.uploadImage(file);
            response=ResponseEntity.status(HttpStatus.OK).body(uploadImage);
        }
        catch (Exception e){
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename){
        byte[] imageData = imageService.downloadImage(filename);
        ResponseEntity response = null;

        try{
            response=ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        }
        catch (Exception e){
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return response;
    }
}
