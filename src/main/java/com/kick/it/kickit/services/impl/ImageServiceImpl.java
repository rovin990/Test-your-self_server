package com.kick.it.kickit.services.impl;

import com.kick.it.kickit.Utility.ImageUtil;
import com.kick.it.kickit.entities.ImageData;
import com.kick.it.kickit.repository.ImageRepository;
import com.kick.it.kickit.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public byte[] downloadImage(String filename) {
        Optional<ImageData> dbImageData = imageRepository.findByName(filename);
        byte[] images=ImageUtil.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
