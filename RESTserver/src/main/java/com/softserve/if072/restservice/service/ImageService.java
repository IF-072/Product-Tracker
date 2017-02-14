package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.dao.mybatisdao.ImageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageDAO imageDAO;

    public List<Image> getAll() {
        return imageDAO.getAll();
    }

    public Image getById(int id) {
        return imageDAO.getByID(id);
    }

    public Image getByFileName(String fileName) { return imageDAO.getByFileName(fileName); }

    public void insert(Image image) {
        imageDAO.insert(image);
    }

    public void update(Image image) {
        imageDAO.update(image);
    }

    public void delete(int id) {
        imageDAO.deleteById(id);
    }
}
