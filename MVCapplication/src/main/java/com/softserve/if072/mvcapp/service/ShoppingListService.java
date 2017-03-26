package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This class contains methods for obtaining and changing a shopping list.
 *
 * @author Oleh Pochernin
 */
@Service
public class ShoppingListService {
    private static final Logger LOG = LogManager.getLogger(ShoppingListService.class);
    private static final String INFO_LOG_TEMPLATE = "Shopping list element (userId: %d, productId: %d) has been %s.";

    @Value("${service.shoppingList}")
    private String shoppingListUrl;

    @Value("${service.shoppingList.byUser}")
    private String shoppingListByUserUrl;

    @Value("${service.shoppingList.byUserAndProduct}")
    private String shoppingListByUserAndProductUrl;

    @Value("${service.product.id}")
    private String productById;

    private final RestTemplate restTemplate;

    private UserService userService;

    @Autowired
    public ShoppingListService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    /**
     * This method receives all shopping list's elements from RESTful service and returns them.
     *
     * @return all shopping list's elements for user
     */
    public List<ShoppingList> getAllElements() {
        List<ShoppingList> shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserUrl, userService.getCurrentUser().getId()), List.class);

        if (CollectionUtils.isNotEmpty(shoppingList)) {
            LOG.info("Shopping list with {} elements has been received.", shoppingList.size());
        } else {
            LOG.info("Shopping list has't got any elements yet.");
        }

        return shoppingList;
    }

    /**
     * This method edits product amount and returns new product amount with units.
     *
     * @param prodId id of the editing product
     * @param value  product amount is changed by this value
     * @return new product amount with units
     */
    public String editShoppingList(int prodId, int value) {
        ShoppingList shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserAndProductUrl,
                        userService.getCurrentUser().getId(), prodId), ShoppingList.class);

        HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);

        int newAmount = shoppingList.getAmount() + value;
        if (newAmount < 1) {
            shoppingList.setAmount(1);
        } else {
            shoppingList.setAmount(newAmount);
        }

        restTemplate.exchange(shoppingListUrl, HttpMethod.PUT, entity, ShoppingList.class);
        LOG.info(String.format(INFO_LOG_TEMPLATE,
                shoppingList.getUser().getId(), shoppingList.getProduct().getId(), "updated"));

        StringBuilder response = new StringBuilder();
        response.append(shoppingList.getAmount());

        Unit unit = shoppingList.getProduct().getUnit();
        if (unit != null) {
            response.append(" ").append(unit.getName());
        }

        return response.toString();
    }

    /**
     * This method deletes product from the shopping list.
     *
     * @param prodId id of the deleting product
     */
    public void deleteProductFromShoppingList(int prodId) {
        ShoppingList shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserAndProductUrl,
                        userService.getCurrentUser().getId(), prodId), ShoppingList.class);

        HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);

        restTemplate.exchange(shoppingListUrl, HttpMethod.DELETE, entity, ShoppingList.class);
        LOG.info(String.format(INFO_LOG_TEMPLATE,
                shoppingList.getUser().getId(), shoppingList.getProduct().getId(), "deleted"));
    }

    /**
     * This method adds product to the shopping list
     *
     * @param productId id of an added product
     */
    public void addProductToShoppingList(int productId) {
        User user = userService.getCurrentUser();

        ShoppingList shoppingList = restTemplate.getForObject(
                String.format(shoppingListByUserAndProductUrl, user.getId(), productId), ShoppingList.class);

        if (shoppingList == null) {
            shoppingList = new ShoppingList();
            Product product = restTemplate.getForObject(
                    String.format(productById, productId), Product.class);

            shoppingList.setUser(user);
            shoppingList.setProduct(product);
            shoppingList.setAmount(1);

            HttpEntity<ShoppingList> entity = new HttpEntity<>(shoppingList);
            restTemplate.exchange(shoppingListUrl, HttpMethod.POST, entity, ShoppingList.class);
            LOG.info(String.format(INFO_LOG_TEMPLATE,
                    shoppingList.getUser().getId(), shoppingList.getProduct().getId(), "added"));
        }
    }
}
