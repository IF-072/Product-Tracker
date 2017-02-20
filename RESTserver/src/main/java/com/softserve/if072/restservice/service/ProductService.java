package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * The class contains methods to add, read and delete products from database
 *
 * @author Vitaliy Malisevych
 */

@Service
@PropertySource(value = {"classpath:message.properties"})
public class ProductService {

    private ProductDAO productDAO;

    @Value("${product.notFound}")
    private String productNotFound;

    @Autowired
    public ProductService (ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(int userId) throws DataNotFoundException {
        List<Product> getProducts = productDAO.getAllByUserId(userId);
        List<Product> products = new ArrayList<Product>();
        if (!getProducts.isEmpty()){
            for(Product getProduct : getProducts) {
                if(getProduct.isEnabled()) {products.add(getProduct);}
            }
            return products;
        } else {
            throw new DataNotFoundException("Products not found");
        }
    }

    @Transactional
    public Product getProductById(int id) throws DataNotFoundException {
        Product product = productDAO.getByID(id);
        if (product != null){
            return product;
        } else {
            throw new DataNotFoundException(String.format(productNotFound, id));
        }
    }

    @Transactional
    public void addProduct(Product product) {productDAO.insert(product);}

    @Transactional
    public void updateProduct(Product product) throws DataNotFoundException {productDAO.update(product);}

    @Transactional
    public void updateProductByImage(Product product) throws DataNotFoundException {productDAO.updateImage(product);}

    @Transactional
    public void deleteProduct(int id) throws DataNotFoundException {
        Product product = productDAO.getByID(id);
        if (product != null){
            productDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(productNotFound, id));
        }
    }

    @Transactional
    public List<Product> getProductsByStoreId(int storeId) throws DataNotFoundException {
        List<Product> products = productDAO.getProductsByStoreId(storeId);
        if (!products.isEmpty()){
            return products;
        } else {
            throw new DataNotFoundException("Products not found");
        }
    }

    @Transactional
    public List<Store> getStoresByProductId(int productId, int userId) throws DataNotFoundException {
        List<Store> stores = productDAO.getStoresByProductId(productId, userId);
        if (CollectionUtils.isNotEmpty(stores)){
            return stores;
        } else {
            throw new DataNotFoundException("Stores not found");
        }
    }

    @Transactional
    public void deleteStoreFromProductById(Product product) throws DataNotFoundException {

        for(Store s : product.getStores()) {
            Store store = productDAO.getStoreFromProductById(s.getId(), product.getId());
            if (store != null) {
                productDAO.deleteStoreFromProductById(store.getId(), product.getId());
            } else {
                throw new DataNotFoundException(String.format("Store %d from product %d not found", store.getId(), product.getId()));
            }
        }

    }

    @Transactional
    public void addStoreToProduct(Product product) {

        for(Store s : product.getStores()) {
            productDAO.addStoreToProduct(s.getId(), product.getId());
        }

    }
}
