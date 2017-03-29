package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains methods to add, read and delete products from database
 *
 * @author Vitaliy Malisevych
 */

@Service
public class ProductService {

    public static final Logger LOGGER =  LogManager.getLogger(ProductService.class);

    private ProductDAO productDAO;

    @Value("${product.notFound}")
    private String productNotFound;

    @Autowired
    public ProductService (ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(int userId) {
        List<Product> getProducts = productDAO.getAllByUserId(userId);
        List<Product> products = new ArrayList<>();
        if (!getProducts.isEmpty()){
            for(Product getProduct : getProducts) {
                if(getProduct.isEnabled()) {products.add(getProduct);}
            }
        }
        return products;
    }

    public Product getProductById(int id) {
        Product product = productDAO.getByID(id);
        if (product != null){
            return product;
        } else {
            throw new DataNotFoundException(String.format(productNotFound, id));
        }
    }

    @Transactional
    public void addProduct(Product product) { productDAO.insert(product); }

    @Transactional
    public void updateProduct(Product product) {
        Product getProduct = productDAO.getByID(product.getId());

        if(getProduct != null) {
            productDAO.update(product);
            LOGGER.info(String.format("Product with ID %d was successfully updated", product.getId()));
        } else {
            throw new DataNotFoundException(String.format(productNotFound, product.getId()));
        }

    }

    @Transactional
    public void updateProductByImage(Product product) {

        Product getProduct = productDAO.getByID(product.getId());

        if(getProduct != null) {
            productDAO.updateImage(product);
            LOGGER.info(String.format("Image of product with ID %d was successfully updated", product.getId()));
        } else {
            throw new DataNotFoundException(String.format(productNotFound, product.getId()));
        }

    }

    @Transactional
    public void deleteProduct(int id) {

        Product product = productDAO.getByID(id);

        if (product != null){
            productDAO.deleteById(id);
            LOGGER.info(String.format("Product with ID %d was successfully deleted", product.getId()));
        } else {
            throw new DataNotFoundException(String.format(productNotFound, id));
        }
    }

    public List<Product> getProductsByStoreId(int storeId) {
        List<Product> products = productDAO.getProductsByStoreId(storeId);
        if (!products.isEmpty()){
            return products;
        } else {
            throw new DataNotFoundException("Products not found");
        }
    }

    public List<Store> getStoresByProductId(int productId, int userId) {

        List<Store> storesInProduct = productDAO.getStoresByProductIdAndUserId(productId, userId);
        List<Store> stores = new ArrayList<>();

        if (!storesInProduct.isEmpty()){
            for(Store getStore : storesInProduct) {
                if(getStore.isEnabled()) {stores.add(getStore);}
            }
            LOGGER.info(String.format("Stores were found in product %d", productId));
        } else {
            LOGGER.error(String.format("Stores were not found in product %d", productId));
        }

        return stores;

    }

    @Transactional
    public void deleteStoreFromProductById(Product product) {

        for(Store s : product.getStores()) {
            Store store = productDAO.getStoreFromProductById(s.getId(), product.getId());
            if (store != null) {
                productDAO.deleteStoreFromProductById(store.getId(), product.getId());
            } else {
                throw new DataNotFoundException(String.format("Store from product %d not found", product.getId()));
            }
        }

    }

    @Transactional
    public void addStoresToProduct(Product product) {

        for(Store s : product.getStores()) {
            productDAO.addStoreToProduct(s.getId(), product.getId());
        }

    }

    public Product getProductByNameAndUserId(String productName, int userId) {

        Product product = productDAO.getProductByNameAndUserId(productName, userId);

        if(product != null) {
            LOGGER.info("Product with id " + product.getId() + " was found.");
            return product;
        } else {
            LOGGER.error("Product with name " + productName + " wasn't found");
            return null;
        }

    }

    public void restoreProduct(Product product) {
        if(getProductById(product.getId()) != null) {
            productDAO.restore(product);
            LOGGER.info("Product with id=" + product.getId() + " was restored");
        } else {
            LOGGER.error("Product with id " + product.getId() + " wasn't found");
        }
    }

    public int getUserIdByProductId(int id) throws DataNotFoundException {
        Integer userId = productDAO.getUserIdByProductId(id);
        if (userId != null) {
            return userId;
        } else {
            throw new DataNotFoundException(String.format(productNotFound, id));
        }
    }
}
