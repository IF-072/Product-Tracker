package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.service.ShoppingListService;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The ShoppingListControllerTest class is used to test ShoppingListController class.
 *
 * @author Oleh Pochernin
 */
@RunWith(MockitoJUnitRunner.class)
public class ShoppingListControllerTest {
    private static final String PRODUCT_NAME_1 = "молоко";
    private static final String PRODUCT_NAME_2 = "цукор";
    private static final String UNIT_NAME_1 = "л";
    private static final String UNIT_NAME_2 = "кг";

    @Mock
    private ShoppingListService shoppingListServiceMock;

    @Mock
    private UserService userServiceMock;

    private ShoppingListController shoppingListController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        shoppingListController = new ShoppingListController(shoppingListServiceMock, userServiceMock);
        mockMvc = standaloneSetup(shoppingListController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"))
                .build();
    }

    @Test
    public void getPage_shouldReturnShoppingListViewName() throws Exception {
        User user = new User();
        user.setId(1);

        when(userServiceMock.getCurrentUser()).thenReturn(user);

        Unit unit1 = new Unit();
        unit1.setName(UNIT_NAME_1);

        Unit unit2 = new Unit();
        unit2.setName(UNIT_NAME_2);

        Product product1 = new Product();
        product1.setName(PRODUCT_NAME_1);
        product1.setUnit(unit1);

        Product product2 = new Product();
        product2.setName(PRODUCT_NAME_2);
        product2.setUnit(unit2);

        ShoppingList shoppingList1 = new ShoppingList();
        shoppingList1.setUser(user);
        shoppingList1.setProduct(product1);
        shoppingList1.setAmount(3);

        ShoppingList shoppingList2 = new ShoppingList();
        shoppingList2.setUser(user);
        shoppingList2.setProduct(product2);
        shoppingList2.setAmount(5);

        List<ShoppingList> shoppingList = new ArrayList<>();
        shoppingList.add(shoppingList1);
        shoppingList.add(shoppingList2);

        when(shoppingListServiceMock.getAllElements(userServiceMock.getCurrentUser().getId())).thenReturn(shoppingList);

        mockMvc.perform(get("/shopping_list"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/views/shopping_list.jsp"))
                .andExpect(model().attributeExists("shoppingList"))
                .andExpect(model().attribute("shoppingList", hasSize(2)))
                .andExpect(model().attribute("shoppingList", hasItem(
                        allOf(
                                hasProperty("user", hasProperty("id", is(1))),
                                hasProperty("product", allOf(
                                        hasProperty("name", is(PRODUCT_NAME_1)),
                                        hasProperty("unit", hasProperty("name", is(UNIT_NAME_1)))
                                )),
                                hasProperty("amount", is(3))
                        ))
                )).andExpect(model().attribute("shoppingList", hasItem(
                allOf(
                        hasProperty("user", hasProperty("id", is(1))),
                        hasProperty("product", allOf(
                                hasProperty("name", is(PRODUCT_NAME_2)),
                                hasProperty("unit", hasProperty("name", is(UNIT_NAME_2)))
                        )),
                        hasProperty("amount", is(5))
                ))
        ));
    }
}
