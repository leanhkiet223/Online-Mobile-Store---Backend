package com.onlinemobilestore.services.serviceImpl;

import com.onlinemobilestore.repository.ImageRepository;
import com.onlinemobilestore.services.servicelnterface.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private ImageRepository imageRepository;
    public ImageServiceImpl(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }
}
