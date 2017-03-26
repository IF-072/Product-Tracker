package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Role;
import com.softserve.if072.common.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

/**
 * Contains test methods for UserService class
 *
 * @author Igor Parada
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        userService = new UserService(restTemplate);
        ReflectionTestUtils.setField(userService, "updateUserURL", "test/url/%d/");
        ReflectionTestUtils.setField(userService, "premiumDuration", 10000);
    }

    @Test
    public void prolongPremium_ShouldIncreasePremiumExpiresTime(){
        when(restTemplate.postForEntity(nullable(String.class), nullable(User.class), any())).thenReturn(null);
        User user = new User();
        user.setRole(Role.ROLE_PREMIUM);
        user.setPremiumExpiresTime(0);
        userService.setPremium(user);
        assertTrue(user.getPremiumExpiresTime() > System.currentTimeMillis() / 1000);
        assertEquals(user.getRole(), Role.ROLE_PREMIUM);
    }

    @Test
    public void getCurrentUser_ShouldSetRoleToPremium(){
        when(restTemplate.postForEntity(nullable(String.class), nullable(User.class), any())).thenReturn(null);
        User user = new User();
        user.setRole(Role.ROLE_REGULAR);
        userService.setPremium(user);
        assertEquals(user.getRole(), Role.ROLE_PREMIUM);
    }

}
