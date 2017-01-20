package com.softserve.if072.restservice.service;


import com.softserve.if072.common.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getAll();

    Image getById(int id);

    void insert(Image product);

    void update(Image product);

    void delete(int id);
}
