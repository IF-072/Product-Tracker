package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.dao.mybatisdao.ImageDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Value("${image.notFound}")
    private String imageNotFound;

    @Autowired
    private ImageDAO imageDAO;

    public List<Image> getAll() {
        return imageDAO.getAll();
    }

    public Image getById(int id) throws DataNotFoundException {

        Image image = imageDAO.getByID(id);
        if (image != null){
            return image;
        } else {
            throw new DataNotFoundException(String.format(imageNotFound, id));
        }

    }

    public Image getByFileName(String fileName) throws DataNotFoundException {

        Image image = imageDAO.getByFileName(fileName);
        if (image != null){
            return image;
        } else {
            throw new DataNotFoundException("Image with fileName " + fileName + " not found");
        }
    }

    public void insert(Image image) {
        imageDAO.insert(image);
    }

    public void update(Image image) throws DataNotFoundException {
        imageDAO.update(image);
    }

    public void delete(int id) throws DataNotFoundException {

        Image image = imageDAO.getByID(id);
        if (image != null){
            imageDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(imageNotFound, id));
        }

    }

    public int getLasrInsertId() throws DataNotFoundException { return imageDAO.getLastInsertId(); }
}
