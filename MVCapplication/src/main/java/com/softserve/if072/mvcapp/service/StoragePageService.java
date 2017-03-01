package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.StorageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by dyndyn on 26.02.2017.
 */
@Service
public class StoragePageService {

    private static final Logger LOGGER = LogManager.getLogger(StoragePageService.class);

    @Value("${application.restStorageURL}")
    private String storageUrl;

    @Value("${application.restShoppingListURL}")
    private String shoppingListURL;

    private RestTemplate restTemplate;

    @Autowired
    public StoragePageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Storage> getStorages(int userId) {
        final String uri = storageUrl + userId;
        List<Storage> storage = restTemplate.getForObject(uri, List.class);

        if (CollectionUtils.isEmpty(storage)) {
            LOGGER.error("Storage' list of user with id {} is empty", userId);
        } else {
            LOGGER.info("Storage' list of user with id {} has been received.", userId);
        }
        return storage;
    }

    public String updateAmount(StorageDTO storageDTO, BindingResult result) {
        if (result.hasErrors()) {
            String message = "";
            for (FieldError error : result.getFieldErrors()) {
                message += error.getDefaultMessage() + "\r\n";
            }
            return message;
        }
        final String uri = storageUrl + "dto";
        restTemplate.put(uri, storageDTO);
        return "";
    }

}
