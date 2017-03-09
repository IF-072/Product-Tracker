package com.softserve.if072.mvcapp.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Unit;
import com.softserve.if072.common.model.User;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
import com.softserve.if072.mvcapp.service.ProductPageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * The class is used to test ProductPageController class methods
 *
 * @author Vitaliy Malisevych
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductPageControllerTest {

    @Mock
    private ProductPageService productPageService;

    @Mock
    private UserService userService;

    private User user;
    private Product product;
    private Unit unit;
    private Category category;

    @InjectMocks
    private ProductPageController productPageController;
    private MockMvc mockMvc;

    @Before
    public void setup() throws ClassNotFoundException, NoSuchMethodException {
        mockMvc = standaloneSetup(productPageController)
                .setViewResolvers(new InternalResourceViewResolver("/WEB-INF/views/product/", ".jsp"))
                .build();
        product = new Product();
        user = new User();
        unit = new Unit();
        category = new Category();
    }

    @Test
    public void testGetProductPage_ShouldReturnProductViewName() throws Exception {
        List<Product> products = Arrays.asList(product, product);
        when(productPageService.getAllProducts(anyInt())).thenReturn(products);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(forwardedUrl("/WEB-INF/views/product/product.jsp"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)));
        verify(productPageService,times(1)).getAllProducts(anyInt());
        verifyNoMoreInteractions(productPageService);
    }

    @Test
    public void testGetProductPage_ShouldReturnProductViewNameIfListIsEmpty() throws Exception {
        List<Product> products = Arrays.asList();
        when(productPageService.getAllProducts(anyInt())).thenReturn(products);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(forwardedUrl("/WEB-INF/views/product/product.jsp"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(0)));
        verify(productPageService,times(1)).getAllProducts(anyInt());
    }

    @Test
    public void testAddProduct_ShouldReturnViewName() throws Exception {
        List<Unit> units = Arrays.asList(unit);
        List<Category> categories = Arrays.asList(category);
        when(productPageService.getAllUnits()).thenReturn(units);
        when(productPageService.getAllCategories(anyInt())).thenReturn(categories);
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/product/addProduct"))
                .andExpect(status().isOk())
                .andExpect(view().name("addProduct"))
                .andExpect(forwardedUrl("/WEB-INF/views/product/addProduct.jsp"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("units"))
                .andExpect(model().attribute("units", hasSize(1)))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(1)));
        verify(productPageService,times(1)).getAllUnits();
        verify(productPageService,times(1)).getAllCategories(anyInt());
        verifyNoMoreInteractions(productPageService);
    }

    @Test
    public void testAddProduct_POST_ShouldReturnViewName() throws Exception {
        String name = createStringField(15);
        String description = createStringField(30);
        Product product = createProduct(name, description, createUnit(1,"unitName"), createCategory(2,"categoryName"));
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", Integer.toString(product.getUnit().getId()))
                .param("category.id", Integer.toString(product.getCategory().getId()))
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/product/"));
        verify(productPageService,times(1)).addProduct(product, userService.getCurrentUser());
        verify(productPageService,times(1)).isAlreadyExist(product, userService.getCurrentUser());
        verify(productPageService,times(1)).isDeleted(product, userService.getCurrentUser());
        verifyNoMoreInteractions(productPageService);
    }

    @Test
    public void testAddProduct_POST_ShouldReturnValidationName() throws Exception {
        String name = createStringField(1);
        String description = createStringField(30);
        Product product = createProduct(name, description, createUnit(1,"unitName"), createCategory(2,"categoryName"));
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", Integer.toString(product.getUnit().getId()))
                .param("category.id", Integer.toString(product.getCategory().getId()))
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product", "name"))
                .andExpect(view().name("addProduct"));
        verify(productPageService,times(0)).addProduct(product, userService.getCurrentUser());
    }

    @Test
    public void testAddProduct_POST_ShouldReturnValidationDescription() throws Exception {
        String name = createStringField(10);
        String description = createStringField(256);
        Product product = createProduct(name, description, createUnit(1,"unitName"), createCategory(2,"categoryName"));
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", Integer.toString(product.getUnit().getId()))
                .param("category.id", Integer.toString(product.getCategory().getId()))
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product", "description"))
                .andExpect(view().name("addProduct"));
        verify(productPageService,times(0)).addProduct(product, userService.getCurrentUser());
    }

    @Test
    public void testAddProduct_POST_ShouldReturnValidationUnit() throws Exception {
        String name = createStringField(10);
        String description = createStringField(30);
        Product product = createProduct(name, description, null, createCategory(2,"categoryName"));
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", "-1")
                .param("category.id", Integer.toString(product.getCategory().getId()))
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product", "unit"))
                .andExpect(view().name("addProduct"));
        verify(productPageService,times(0)).addProduct(product, userService.getCurrentUser());
    }

    @Test
    public void testAddProduct_POST_ShouldReturnValidationCategory() throws Exception {
        String name = createStringField(10);
        String description = createStringField(30);
        Product product = createProduct(name, description, createUnit(1,"unitName"), null);
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", Integer.toString(product.getUnit().getId()))
                .param("category.id", "-1")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("product", "category"))
                .andExpect(view().name("addProduct"));
        verify(productPageService,times(0)).addProduct(product, userService.getCurrentUser());
    }

    @Test
    public void testAddProduct_ShouldReturnIsAlreadyExists() throws Exception {
        String name = createStringField(15);
        String description = createStringField(30);
        Product product = createProduct(name, description, createUnit(1,"unitName"), createCategory(2,"categoryName"));
        when(userService.getCurrentUser()).thenReturn(user);
        when(productPageService.isAlreadyExist(any(Product.class), any(User.class))).thenReturn(true);
        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", Integer.toString(product.getUnit().getId()))
                .param("category.id", Integer.toString(product.getCategory().getId()))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("addProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("units"));
        verify(productPageService,times(1)).isAlreadyExist(product, userService.getCurrentUser());
        verify(productPageService,times(0)).addProduct(product, userService.getCurrentUser());
        verify(productPageService,times(0)).isDeleted(product, userService.getCurrentUser());
    }

    @Test
    public void testAddProduct_ShouldReturnIsDeleted() throws Exception {
        String name = createStringField(15);
        String description = createStringField(30);
        Product product = createProduct(name, description, createUnit(1,"unitName"), createCategory(2,"categoryName"));
        when(userService.getCurrentUser()).thenReturn(user);
        when(productPageService.isDeleted(any(Product.class), any(User.class))).thenReturn(true);
        mockMvc.perform(post("/product/addProduct")
                .sessionAttr("product", product)
                .param("name",name)
                .param("description", description)
                .param("unit.id", Integer.toString(product.getUnit().getId()))
                .param("category.id", Integer.toString(product.getCategory().getId()))
        )
                .andExpect(status().isOk())
                .andExpect(view().name("existsProduct"));
        verify(productPageService,times(0)).addProduct(product, userService.getCurrentUser());
    }

    @Test
    public void testEditProduct_ShouldReturnViewName() throws Exception {
        List<Unit> units = Arrays.asList(unit);
        List<Category> categories = Arrays.asList(category);
        when(productPageService.getAllUnits()).thenReturn(units);
        when(productPageService.getAllCategories(anyInt())).thenReturn(categories);
        when(productPageService.getProduct(anyInt())).thenReturn(createProduct("Name", "Description",
                createUnit(1, "unit"), createCategory(2, "category")));
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(get("/product/editProduct?productId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(forwardedUrl("/WEB-INF/views/product/editProduct.jsp"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("units"))
                .andExpect(model().attribute("units", hasSize(1)))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(1)));
        verify(productPageService,times(1)).getAllUnits();
        verify(productPageService,times(1)).getAllCategories(anyInt());
        verify(productPageService,times(1)).getProduct(anyInt());
        verifyNoMoreInteractions(productPageService);
    }

    @Test
    public void testGetStoresByProductId_ShouldReturnViewName() throws Exception {
        Map<Integer, String> storesId = new HashMap<>();
        storesId.put(1, "name1");
        StoresInProduct storesInProduct = new StoresInProduct();

        when(productPageService.getAllStoresId(anyInt())).thenReturn(storesId);
        when(productPageService.getStoresInProduct(anyInt())).thenReturn(storesInProduct);
        when(productPageService.getProduct(anyInt())).thenReturn(product);
        when(userService.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/product/stores?productId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("productInStores"))
                .andExpect(forwardedUrl("/WEB-INF/views/product/productInStores.jsp"))
                .andExpect(model().attributeExists("storesId"))
                .andExpect(model().attributeExists("storesInProduct"))
                .andExpect(model().attributeExists("product"));
        verify(productPageService,times(1)).getAllStoresId(anyInt());
        verify(productPageService,times(1)).getStoresInProduct(anyInt());
        verify(productPageService,times(1)).getProduct(anyInt());
    }

    @Test
    public void testGetStoresByProductId_POST_ShouldReturnViewName() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user);
        mockMvc.perform(post("/product/stores")
                .param("productId", "1")
                .sessionAttr("storesInProduct", new StoresInProduct())
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/product/"));
        verify(productPageService,times(1)).updateStoresInProduct(any(StoresInProduct.class),
                anyInt());
    }

    private Product createProduct(String name, String description, Unit unit, Category category) {
        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setUnit(unit);
        product.setCategory(category);

        return product;
    }

    private Unit createUnit(int id, String name) {
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);

        return unit;
    }

    private Category createCategory(int id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);

        return category;
    }

    private String createStringField(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i ++) {
            sb.append("v");
        }

        return sb.toString();
    }
}
