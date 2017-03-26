package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.controller.ExceptionHandlerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    private RestTemplate restTemplate;

    @Value("${service.user.current}")
    private String getCurrentUserURL;

    @Value("${service.user.update}")
    private String updateUserURL;

    @Value("${application.premiumDurationInSeconds}")
    private long premiumDuration;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Method provides access to current logged in {@link User} instance. If current user is not logged in, throws
     * {@link HttpClientErrorException}, which should be caught and handled by {@link ExceptionHandlerController}
     *
     * @return current User instance
     */
    public User getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            user = restTemplate.getForObject(getCurrentUserURL, User.class);
            session.setAttribute("user", user);
        }
        return user;
    }

    /**
     * Sets user's account type to 'premium' if current value is 'regular'
     *
     * @param user user to have role updated
     */
    public void setPremium(User user) {
        if (user.getRole() != null && user.getRole().isRegular()) {
            user.setRole(Role.ROLE_PREMIUM);
            prolongPremium(user);
        }
    }

    /**
     * Plolongs the user's 'premium' account
     *
     * @param user user to have premium period updated
     */
    public void prolongPremium(User user) {
        if (user.getRole() != null && user.getRole().isPremium()) {
            long premiumExpiresTime = System.currentTimeMillis() / 1000L + premiumDuration;
            user.setPremiumExpiresTime(premiumExpiresTime);
            updateUser(user);
        }
    }

    /**
     * Sends the updated user to REST service
     *
     * @param user user to be updated
     */
    private void updateUser(User user) {
        restTemplate.postForEntity(String.format(updateUserURL, user.getId()), user, String.class);
    }
}
