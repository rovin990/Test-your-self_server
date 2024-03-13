package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.Utility.ImageUtil;
import com.kick.it.kickit.entities.ImageData;
import com.kick.it.kickit.repository.ImageRepository;
import com.kick.it.kickit.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;



    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = new ImageData(file.getOriginalFilename(), file.getContentType(), ImageUtil.compressImage(file.getBytes()));
        ImageData local= imageRepository.save(imageData);
        if(local!=null){
            return "file uploaded successfully : " + local.getName();
        }

        return null;
    }

    @Override
    public String uploadOptionImage(MultipartFile file,Long questionId) throws IOException {
        ImageData imageData = new ImageData("option_"+questionId+"_"+file.getOriginalFilename(), file.getContentType(), ImageUtil.compressImage(file.getBytes()));
        ImageData local= imageRepository.save(imageData);
        if(local!=null){
            return "file uploaded successfully : " + local.getName();
        }

        return null;
    }

    @Override
    public String uploadQuestionImage(MultipartFile file, Long questionId) throws IOException {
        ImageData imageData = new ImageData("question_"+questionId+"_"+file.getOriginalFilename(), file.getContentType(), file.getBytes());
        ImageData local= imageRepository.save(imageData);
        if(local!=null){
            return "file uploaded successfully : " + local.getName();
        }

        return null;
    }


    @Override
    public byte[] downloadImage(String filename) {
        Optional<ImageData> dbImageData = imageRepository.findByName(filename);
        byte[] images=dbImageData.get().getImageData();

        byte[] base64=Base64.getEncoder().encode(images);
        System.out.println((base64));
        return base64;
    }

    @Override
    public void deleteImage(String filename) {
        ImageData local = imageRepository.findByName(filename).get();
        imageRepository.delete(local);
    }


}
