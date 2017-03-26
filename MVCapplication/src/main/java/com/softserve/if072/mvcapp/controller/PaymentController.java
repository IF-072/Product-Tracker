package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The controller contains methods that handle payment page
 *
 * @author Igor Parada
 */
@Controller
public class PaymentController {

    private UserService userService;

    @Autowired
    public PaymentController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles payment page displaying
     *
     * @return view name
     */
    @GetMapping("/payment")
    public String displayPaymentPage() {
        User user = userService.getCurrentUser();
        return user != null ? "payment" : "redirect:/login";
    }
}
