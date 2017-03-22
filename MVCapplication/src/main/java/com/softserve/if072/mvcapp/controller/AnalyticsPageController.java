package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.dto.AnalyticsDTO;
import com.softserve.if072.mvcapp.service.AnalyticsPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class provides access to the analytics page.
 */
@Controller
@RequestMapping("/analytics")
public class AnalyticsPageController {
    private static final Logger LOG = LogManager.getLogger(AnalyticsPageController.class);

    @Value("${service.url.analytics}")
    private String restAnalyticsURL;

    private UserService userService;
    private AnalyticsPageService analyticsPageService;

    @Autowired
    public AnalyticsPageController(UserService userService, AnalyticsPageService analyticsPageService) {
        this.userService = userService;
        this.analyticsPageService = analyticsPageService;
    }

    /**
     * This method returns an analytics view.
     *
     * @return analytics view url
     */
    @GetMapping
    public String getAnalyticsPage(Model model) {
        model.addAttribute("products", analyticsPageService.getAllProducts(userService.getCurrentUser().getId()));
        model.addAttribute("analyticsDTO", new AnalyticsDTO());

        return "analytics";
    }

}