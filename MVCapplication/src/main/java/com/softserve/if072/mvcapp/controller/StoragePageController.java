package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.User;
import com.softserve.if072.common.model.dto.StorageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by dyndyn on 05.02.2017.
 */
@Controller
@RequestMapping("/storage")
@PropertySource(value = {"classpath:application.properties"})
public class StoragePageController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageController.class);

    @Value("${application.restStorageURL}")
    private String storageUrl;

    @Value("${application.restShoppingListURL}")
    private String shoppingListURL;

    @GetMapping
    public String getPage(ModelMap model) {

        int userId = getCurrentUser().getId();

        final String uri = storageUrl + userId;
        RestTemplate restTemplate = getRestTemplate();
        List<Storage> list = restTemplate.getForObject(uri, List.class);

        model.addAttribute("list", list);
        model.addAttribute("storage", new StorageDTO());
        return "storage";

    }

    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateAmount(@ModelAttribute StorageDTO storageDTO) {
        storageDTO.setUserId(getCurrentUser().getId());
        final String uri = storageUrl + "dto";

        RestTemplate restTemplate = getRestTemplate();
        restTemplate.put(uri, storageDTO);
        LOGGER.info("Amount is updated");


    }

    @PostMapping("/addToSL")
    @ResponseStatus(value = HttpStatus.OK)
    public void addToShoppingList(@RequestParam("userId") int userId, @RequestParam("productId") int productId) {
        ShoppingList shoppingList = new ShoppingList(new User(), new Product(), 1);
        shoppingList.getUser().setId(userId);
        shoppingList.getProduct().setId(productId);

        RestTemplate restTemplate = getRestTemplate();
        restTemplate.postForObject(shoppingListURL, shoppingList, ShoppingList.class);
        LOGGER.info("SoppingList is inserted");
    }
}
