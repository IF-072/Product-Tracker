package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
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

    @Transactional
    public List<Product> getAllProducts(int userId) throws DataNotFoundException {
        List<Product> products = productDAO.getAllByUserId(userId);
        if (!products.isEmpty()){
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
    public List<Store> getStoresByProductId(int productId) throws DataNotFoundException {
        List<Store> stores = productDAO.getStoresByProductId(productId);
        if (!stores.isEmpty()){
            return stores;
        } else {
            throw new DataNotFoundException("Stores not found");
        }
    }

    @Transactional
    public void deleteStoreFromProduct(Store store, Product product) throws DataNotFoundException {
        List<Store> stores = productDAO.getStoresByProductId(product.getId());
        for(Store s : stores) {
            if(s.getId() == store.getId()) {
                productDAO.deleteStoreFromProductById(store.getId(), product.getId());
            } else {
                throw new DataNotFoundException("Store not found in product");
            }
        }
    }

    @Transactional
    public void addStoreToProduct(Store store, Product product) {
        productDAO.addStoreToProduct(store, product);
    }
}
