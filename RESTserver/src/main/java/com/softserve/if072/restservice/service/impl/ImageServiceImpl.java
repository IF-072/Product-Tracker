package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.restservice.dao.mybatisdao.ImageDAO;
import com.softserve.if072.restservice.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDAO imageDAO;

    @Override
    public List<Image> getAll() {
        return imageDAO.getAll();
    }

    @Override
    public Image getById(int id) {
        return imageDAO.getByID(id);
    }

    @Override
    public void insert(Image image) {
        imageDAO.insert(image);
    }

    @Override
    public void update(Image image) {
        imageDAO.update(image);
    }

    @Override
    public void delete(int id) {
        imageDAO.delete(id);
    }
}
