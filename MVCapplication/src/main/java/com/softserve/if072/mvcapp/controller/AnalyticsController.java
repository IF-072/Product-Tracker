package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.ProductStatistics;
import com.softserve.if072.common.model.dto.AnalyticsProductDTO;
import com.softserve.if072.mvcapp.service.AnalyticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The AnalyticsController class handles requests for "/analytics" and renders appropriate view
 *
 * @author Igor Kryviuk
 * @author Igor Parada
 */

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private static final String PRODUCT_STATISTICS = "productStatistics";

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Handles requests for getting list of all user's products
     *
     * @param session - HttpSession object
     * @param model   - a map that will be handed off to the view for rendering the data to the client
     * @return - string with appropriate view name
     */
    @GetMapping()
    public String getProducts(HttpSession session, Model model) {
        ProductStatistics productStatistics = (ProductStatistics) session.getAttribute(PRODUCT_STATISTICS);
        if (productStatistics != null) {
            model.addAttribute(PRODUCT_STATISTICS, productStatistics);
            return "analytics";
        }
        List<AnalyticsProductDTO> analyticsProductDTOs = analyticsService.getProducts();

        model.addAttribute("analyticsProductDTOs", analyticsProductDTOs);

        return "selectProductForAnalytics";
    }

    /**
     * Handles requests for getting analytics about concrete product
     *
     * @param model     - a map that will be handed off to the view for rendering the data to the client
     * @param productId - product unique identifier
     * @return string with appropriate view name
     */
    @GetMapping("/{productId}")
    public String getProductStatistics(HttpSession session, Model model, @PathVariable int productId) {
        ProductStatistics productStatistics = analyticsService.getProductStatistics(productId);
        model.addAttribute(PRODUCT_STATISTICS, productStatistics);
        session.setAttribute(PRODUCT_STATISTICS, productStatistics);

        return "analytics";
    }

    /**
     * Handles requests for clean ProductStatistics session object
     *
     * @return string with appropriate view name
     */
    @GetMapping("/cleanSession")
    public String cleanProductStatisticsSessionObject() {
        analyticsService.cleanProductStatisticsSessionObject();

        return "redirect:/analytics";
    }
}