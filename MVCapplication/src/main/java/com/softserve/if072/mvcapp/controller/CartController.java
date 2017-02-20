package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Cart;
import com.softserve.if072.common.model.dto.CartDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * The CartController class is used to retrieve appropriate resources from the
 * REST server
 *
 * @author Igor Kryviuk
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Value("${application.restCartURL}")
    private String restCartURL;
    @Value("${application.restCartDeleteURL}")
    private String restCartDeleteURL;
    @Value("${cart.found}")
    private String cartFound;
    @Value("${cart.buyingSubmint}")
    private String cartBoughtSubmint;
    @Value("${cart.SuccessfullyOperation}")
    private String successfullyOperation;

    @GetMapping
    public String getCart(Model model) {
        RestTemplate template = getRestTemplate();
        List<Cart> carts = template.getForObject(String.format(restCartURL, getCurrentUser().getId()), List.class);
        model.addAttribute("carts", carts);
        LOGGER.info(String.format(cartFound, getCurrentUser().getId(), carts.size()));
        if (CollectionUtils.isNotEmpty(carts)) {
            return "cart";
        }
        return "emptyCart";
    }

    /**
     * Implements behavior of product buying process.
     * Delete current product from the user cart and store it to the user storage.
     * If user storage already has the same product, product amount will be increased,
     * otherwise new product with buying amount will be stored in the user cart.
     *
     * @param userId    - current user unique identifier
     * @param storeId   - store unique identifier
     * @param productId - product unique identifier
     * @param amount    - amount of the product to be bought
     * @return string with appropriate redirect statement
     */
    @PostMapping("/bought")
    public String productBuying(@RequestParam int userId, int storeId, int productId, int amount) {
        CartDTO cartDTO = new CartDTO(userId, storeId, productId, amount);
        LOGGER.info(String.format(cartBoughtSubmint, productId));

        if (isProductInStorage(productId)) {
            // todo: implement updating existing product in the storage
        } else {
            // todo: implement inserting new product to the storage
        }

        template.delete(String.format(restCartDeleteURL, getCurrentUser().getId(), productId));
        LOGGER.info(String.format(successfullyOperation, userId, "bought", amount, productId));
        return "redirect: /cart/";
    }

// todo: implementing this method require:
//    1) implement method public Storage getByProductId(int productId) in the StorageController;

    private boolean isProductInStorage(int productId) {

        return true;
    }

    @GetMapping("/delete")
    public String deleteProductFromCart(@RequestParam int userId, int productId, int amount) {
        template.delete(String.format(restCartDeleteURL, getCurrentUser().getId(), productId));
        LOGGER.info(String.format(successfullyOperation, userId, "deleted", amount, productId));
        return "redirect: /cart/";
    }
}
