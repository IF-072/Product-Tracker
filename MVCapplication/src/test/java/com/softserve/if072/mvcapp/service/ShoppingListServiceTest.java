package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserService userService;

    @InjectMocks
    private ShoppingListService shoppingListService;

    private ShoppingList elem1;

    private ShoppingList elem2;

    @Before
    public void setup() {

        User user = new User();
        user.setId(1);

        when(userService.getCurrentUser()).thenReturn(user);

        elem1 = new ShoppingList();
        elem2 = new ShoppingList();
    }

    @Test
    public void getAllElements_shouldReturnListOfAllShoppingListElements() {
        List<ShoppingList> shoppingList = Arrays.asList(elem1, elem2);
        when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(shoppingList);

        ReflectionTestUtils.setField(shoppingListService,
                "shoppingListByUserUrl", "http://localhost:8080/rest/api/shopping_list/%d");

        assertEquals(shoppingList, shoppingListService.getAllElements());
        verify(restTemplate).getForObject(anyString(), eq(List.class));
    }
}
