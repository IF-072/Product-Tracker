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
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The ShoppingListControllerTest class is used to test ShoppingListController class.
 *
 * @author Oleh Pochernin
 */
@RunWith(MockitoJUnitRunner.class)
public class ShoppingListControllerTest {
    private static final int PRODUCT_ID = 1;
    private static final int PRODUCT_EDIT_VAL = 1;
    private static final String PRODUCT_AMOUNT = "3";
    private static final String PRODUCT_NAME_1 = "молоко";
    private static final String PRODUCT_NAME_2 = "цукор";
    private static final String UNIT_NAME_1 = "л";
    private static final String UNIT_NAME_2 = "кг";

    private User user;

    @Mock
    private ShoppingListService shoppingListServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        ShoppingListController shoppingListController = new ShoppingListController(shoppingListServiceMock);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();

        stringConverter.setSupportedMediaTypes(
                Collections.singletonList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
        mockMvc = standaloneSetup(shoppingListController)
                .setMessageConverters(stringConverter)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"))
                .build();
    }

    @Test
    public void getPage_shouldReturnShoppingListViewName() throws Exception {
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

        when(shoppingListServiceMock.getAllElements()).thenReturn(shoppingList);

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

    @Test
    public void editShoppingList_shouldReturnNewProductAmountWithUnits() throws Exception {
        String expectedData = String.format("%s %s", PRODUCT_AMOUNT, UNIT_NAME_1);

        when(shoppingListServiceMock.editShoppingList(PRODUCT_ID, PRODUCT_EDIT_VAL))
                .thenReturn(expectedData);

        mockMvc.perform(post("/shopping_list/edit")
                .param("prodId", String.valueOf(PRODUCT_ID))
                .param("val", String.valueOf(PRODUCT_EDIT_VAL)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(expectedData));
    }

    @Test
    public void deleteProductFromShoppingList_shouldReturnRedirectToShoppingListPage() throws Exception {
        mockMvc.perform(get("/shopping_list/delete")
                .param("prodId", String.valueOf(PRODUCT_ID)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/shopping_list/"));
    }

    @Test
    public void addProductToShoppingList_shouldReturnRedirectToProductPage() throws Exception {
        mockMvc.perform(post("/shopping_list/add")
                .param("productId", String.valueOf(PRODUCT_ID)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/product/"));
    }
}
