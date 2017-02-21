package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.dao.mybatisdao.StoreDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@PropertySource(value = {"classpath:message.properties"})
public class StoreService {

    private final StoreDAO storeDAO;
    private final ProductDAO productDAO;

    @Value("${store.notFound}")
    private String storeNotFound;

    @Autowired
    public StoreService(StoreDAO storeDAO, ProductDAO productDAO) {
        this.storeDAO = storeDAO;
        this.productDAO = productDAO;
    }

    /**
     * Returns all user stores
     *
     * @param userId - user whose stores will be returned
     * @return - list of user stores
     * @throws DataNotFoundException - if stores not found
     */
    @Transactional
    public List<Store> getAllStores(int userId) throws DataNotFoundException {
        List<Store> stores = storeDAO.getAllStoresByUser(userId);
        List<Store> enabledStores = new ArrayList<>();
        if (!stores.isEmpty()) {
            for (Store getStore : stores) {
                if (getStore.isEnabled()) {
                    enabledStores.add(getStore);
                }
            }
            return enabledStores;
        } else {
            throw new DataNotFoundException("Stores not found");
        }
    }

    /**
     * Returns store from DataBase
     *
     * @param id of store that will be returned
     * @return - store
     * @throws DataNotFoundException - if the store is not found
     */
    @Transactional
    public Store getStoreByID(int id) throws DataNotFoundException {
        Store store = storeDAO.getByID(id);
        if (store == null) {
            throw new DataNotFoundException(String.format(storeNotFound, id));
        } else {
            return store;
        }
    }

    /**
     * Adds new store to DataBase
     *
     * @param store - will be written to DataBase
     * @throws IllegalArgumentException - if the passed store is null or has empty name
     * not found
     */
    @Transactional
    public void addStore(Store store) throws IllegalArgumentException {
        if (store != null && store.getName() != null && store.getName() != "") {
            storeDAO.insert(store);
        } else throw new IllegalArgumentException("Illegal arguments!");
    }

    /**
     * This method  updates store that is in DataBase and has the same id as the passed hire.
     *
     * @param store - will be written
     * @throws IllegalArgumentException - if the passed store has empty name field or the updated store is not found
     */
    @Transactional
    public void updateStore(Store store) throws IllegalArgumentException {
        Store oldStore = storeDAO.getByID(store.getId());
        if (oldStore == null || store.getName().isEmpty() || store.getName() == "") {
            throw new IllegalArgumentException("Illegal arguments!");
        }
        storeDAO.update(store);
    }

    /**
     * This method removes store
     *
     * @param id - store which will be removed
     * @throws DataNotFoundException if the store is not found
     */
    @Transactional
    public void deleteStore(int id) throws DataNotFoundException {
        Store store = storeDAO.getByID(id);
        if (store != null) {
            storeDAO.deleteById(id);
        } else {
            throw new DataNotFoundException(String.format(storeNotFound, id));
        }
    }

    /**
     * This method retrieves products that are presented in the store
     *
     * @param storeId - store where we look for products
     * @param userId  - user whose products and stores we are looking for
     * @return - list of products that are presented in the store
     * @throws DataNotFoundException - if result set is empty
     */
    @Transactional
    public List<Product> getProductsByStoreId(int storeId, int userId) throws DataNotFoundException {
        List<Product> products = storeDAO.getProductsByStoreId(storeId, userId);
        if (products != null) {
            return products;
        } else {
            throw new DataNotFoundException("Products not found");
        }
    }

    /**
     * This method removes product that is presented in the shop
     *
     * @param storeId   - store were the product is presented
     * @param productId - product from this store which will be deleted
     * @throws DataNotFoundException if the product is not presented in this store
     */
    @Transactional
    public void deleteProductFromStoreById(int storeId, int productId) throws DataNotFoundException {
        Product product = storeDAO.getProductFromStoreById(storeId, productId);
        if (product != null) {
            storeDAO.deleteProductFromStoreById(storeId, productId);
        } else {
            throw new DataNotFoundException(String.format("Product %d from Store %d not found", productId, storeId));
        }
    }

    /**
     * This method add product to store
     *
     * @param storeId   - store where product will be added
     * @param productId - product that will be added to store
     * @throws DataNotFoundException - if product or store is not found
     */
    @Transactional
    public void addProductToStore(int storeId, int productId) throws DataNotFoundException {
        storeDAO.addProductToStore(storeId, productId);
    }

    /**
     * This method returns product that is presented in the shop
     *
     * @param storeId   - store were the product is presented
     * @param productId - product from this store
     * @return - if the store contains product
     * @throws DataNotFoundException if the product in return statement is null
     */
    @Transactional
    public Product getProductFromStoreById(int storeId, int productId) throws DataNotFoundException {
        Product product = storeDAO.getProductFromStoreById(storeId, productId);
        if (product != null) {
            return product;
        } else {
            throw new DataNotFoundException(String.format("Product %d from Store %d not found", productId, storeId));
        }
    }

    /**
     * This method retrieves products that are`nt presented in the store. This
     * method is used to add products to store
     *
     * @param storeId - store where we look for products, that are not added there yet
     * @param userId  - user whose products and stores we are looking for
     * @return - set of products that are not added to store
     * @throws DataNotFoundException - if result set is empty
     */
    @Transactional
    public Set<Product> getNotMappedProducts(int storeId, int userId) throws DataNotFoundException {
        Set<Product> storeProducts = new HashSet<>(getProductsByStoreId(storeId, userId));
        Set<Product> allProducts = new HashSet<Product>(productDAO.getEnabledProductsByUserId(userId));

        if (CollectionUtils.isNotEmpty(allProducts)) {
            allProducts.removeAll(storeProducts);

            return allProducts;
        } else {
            throw new DataNotFoundException("Products not found");
        }
    }

    /**
     * This method adds products to store
     *
     * @param storeId    - id of store where products will be added
     * @param productsId - list of productId that will be added to store
     * @throws DataNotFoundException - if list of productId is empty, or store is not found
     */
    @Transactional
    public void addProductsToStore(List<Integer> productsId, int storeId) throws DataNotFoundException {

        Store store = getStoreByID(storeId);
        if (store != null && CollectionUtils.isNotEmpty(productsId)) {
            for (int id : productsId) {

                addProductToStore(id, storeId);
            }
        } else {
            throw new DataNotFoundException(String.format("Products not added to store %d", storeId));
        }
    }

}

