package com.kick.it.kickit.controllers;

import com.kick.it.kickit.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping(value = "/category",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    @PostMapping(value = "/question",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadQuestionImage(@RequestParam("questionId") Long questionId,
                                                 @RequestParam("questionImage") MultipartFile questionImage) {
        System.out.println("Upload question image controller");
        ResponseEntity response=null;
        try{
            String uploadImage= imageService.uploadQuestionImage(questionImage,questionId);
            response=ResponseEntity.status(HttpStatus.OK).body(uploadImage);
        }
        catch (Exception e){
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "/options",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadOptionImage(@RequestParam("questionId") Long questionId,
                                               @RequestParam("option1") MultipartFile option1,
                                               @RequestParam("option2") MultipartFile option2,
                                               @RequestParam("option3") MultipartFile option3,
                                               @RequestParam("option4") MultipartFile option4) {
        List<MultipartFile> optionsImage = Arrays.asList(option1,option2,option3,option4);
        ResponseEntity response=null;
        try{
            List<String> uploadImageList=new ArrayList<>();
            for (MultipartFile image:optionsImage) {
                uploadImageList.add(imageService.uploadOptionImage(image,questionId));
            }

            response=ResponseEntity.status(HttpStatus.OK).body(uploadImageList);
        }
        catch (Exception e){
            response=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return response;
    }

    @GetMapping("")
    public ResponseEntity<byte[]> getImage(@RequestParam String filename){
        System.out.println("filename "+filename);
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

    @DeleteMapping
    public ResponseEntity<?> deleteImage(@RequestParam String filename){
        try {
            imageService.deleteImage(filename);
            return ResponseEntity.status(HttpStatus.OK).body("Question image delete with name : " + filename);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
