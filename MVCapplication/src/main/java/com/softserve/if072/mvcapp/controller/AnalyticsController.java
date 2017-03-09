package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * This class provides access to the analytics page.
 */
@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    private static final Logger LOG = LogManager.getLogger(AnalyticsController.class);

    @Value("${service.url.analytics}")
    private String restAnalyticsURL;

    private UserService userService;
    private RestTemplate restTemplate;

    @Autowired
    public AnalyticsController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    /**
     * This method returns a analytics view.
     *
     * @return analytics view url
     */
    @GetMapping
    public String getAnalyticsPage() {
        restTemplate.getForEntity(String.format(restAnalyticsURL, userService.getCurrentUser().getId()), String.class);
        return "analytics";
    }

}