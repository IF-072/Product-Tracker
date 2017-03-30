package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import com.softserve.if072.restservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Value("${image.notFound}")
    private String imageNotFound;

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public Image getById(int id) throws DataNotFoundException {
        Image image = imageRepository.findOne(id);
        if (image != null){
            return image;
        } else {
            throw new DataNotFoundException(String.format(imageNotFound, id));
        }
    }

    public void insert(Image image) {
        imageRepository.save(image);
    }

    public void update(Image image) throws DataNotFoundException {
        imageRepository.save(image);
    }

    public void delete(int id) throws DataNotFoundException {
        Image image = imageRepository.findOne(id);
        if (image != null){
            imageRepository.delete(id);
        } else {
            throw new DataNotFoundException(String.format(imageNotFound, id));
        }
    }

}