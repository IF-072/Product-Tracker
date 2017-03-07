package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.controller.ExceptionHandlerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;
    private RegistrationService registrationService;

    @Value("${application.authenticationCookieName}")
    private String tokenHeaderName;

    @Value("${service.user.current}")
    private String getCurrentUserURL;

    @Value("${service.user.update}")
    private String updateUserURL;

    @Value("${application.premiumDurationInSeconds}")
    private long premiumDuration;


    @Value("${application.regularRoleId}")
    private int regularRoleId;

    @Value("${application.premiumRoleId}")
    private int premiumRoleId;

    @Autowired
    public UserService(RestTemplate restTemplate, RegistrationService registrationService) {
        this.restTemplate = restTemplate;
        this.registrationService = registrationService;
    }

    /**
     * Method provides access to current logged in {@link User} instance. If current user is not logged in, throws
     * {@link HttpClientErrorException}, which should be caught and handled by {@link ExceptionHandlerController}
     *
     * @return current User instance
     */
    public User getCurrentUser(){
        User user = restTemplate.getForObject(getCurrentUserURL, User.class);
        return user;
    }

    /**
     * Sets user's account type to 'premium' if current value is 'regular'
     *
     * @param user user to have role updated
     */
    public void setPremium(User user){
        if(user.getRole() != null && user.getRole().getId() == regularRoleId){
            Role newRole = registrationService.getRoleByID(premiumRoleId);
            if(newRole != null){
                user.setRole(newRole);
                long premiumExpiresTime = System.currentTimeMillis() / 1000L + premiumDuration;
                user.setPremiumExpiresTime(premiumExpiresTime);
                updateUser(user);
            }
        }
    }

    /**
     * Plolongs the user's 'premium' account
     *
     * @param user user to have premium period updated
     */
    public void prolongPremium(User user){
        if(user.getRole() != null && user.getRole().getId() == premiumRoleId){
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
    public void updateUser(User user) {
        restTemplate.postForEntity(String.format(updateUserURL, user.getId()), user, String.class);
    }
}
