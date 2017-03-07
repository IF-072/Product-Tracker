package com.softserve.if072.restservice.service.security;

import com.softserve.if072.common.model.Image;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.restservice.dao.mybatisdao.ImageDAO;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides security methods for ImageController
 *
 * @author Vitaliy Malisevych
 */
@Service
public class ImageSecurityService extends BaseSecurityService {

    private ImageDAO imageDAO;
    private ProductDAO productDAO;

    @Autowired
    public ImageSecurityService(ImageDAO imageDAO, ProductDAO productDAO) {
        this.imageDAO = imageDAO;
        this.productDAO = productDAO;
    }

    public boolean hasPermissionToAccess(int imageId){

        Image image = imageDAO.getByID(imageId);

        if(image != null) {
            return productDAO.getByImageId(imageId, getCurrentUser().getId()) != null;
        } else {
            return false;
        }

    }

    public boolean hasPermissionToAccess(Image image){

        if(image != null) {
            return productDAO.getByImageId(image.getId(), getCurrentUser().getId()) != null;
        } else {
            return false;
        }

    }

}
