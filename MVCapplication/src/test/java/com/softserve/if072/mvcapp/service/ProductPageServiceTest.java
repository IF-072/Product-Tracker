package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.*;
import com.softserve.if072.mvcapp.dto.StoresInProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * The ProductPageServiceTest class is used to test
 * ProductPageService class methods.
 *
 * @author Vitaliy Malisevych
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductPageServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    ProductPageService productPageService;

    private Product product;
    private User user;
    private Unit unit;
    private Category category;
    private Store store;
    @Before
    public void setUp() throws Exception {
        product = new Product();
        user = new User();
        unit = new Unit();
        category = new Category();
        store = new Store();
        category.setId(1);
        unit.setId(1);
        product.setCategory(category);
        product.setUnit(unit);
    }

    @Test
    public void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        List<Product> products = Arrays.asList(product, product);
        when(restTemplate.getForObject(anyString(),eq(List.class),anyMap())).thenReturn(products);
        assertEquals(products,productPageService.getAllProducts(1));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(List.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getProduct() throws Exception {
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(product);
        assertEquals(product,productPageService.getProduct(1));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(category, category);
        ResponseEntity<List<Category>> categoriesResponse = new ResponseEntity<>(categories, HttpStatus.OK);
        when(restTemplate.exchange(
                any(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Category>>() {}),
                anyMap())
        ).thenReturn(categoriesResponse);
        assertEquals(categories,productPageService.getAllCategories(1));
        verify(restTemplate, times(1)).exchange(
                any(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Category>>() {}),
                anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getAllUnits() throws Exception {
        List<Unit> units = Arrays.asList(unit, unit);
        ResponseEntity<List<Unit>> unitsResponse = new ResponseEntity<>(units, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Unit>>() {}))
        ).thenReturn(unitsResponse);
        assertEquals(units,productPageService.getAllUnits());
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Unit>>() {}));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void addProduct() throws Exception {
        productPageService.addProduct(product, user);
        verify(restTemplate, times(1)).postForObject(anyString(), eq(product), eq(Product.class));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Category.class), anyMap());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Unit.class), anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test(expected = NullPointerException.class)
    public void addProduct_ShouldThrowNullPointerException() throws Exception {
        Product newProduct = new Product();
        productPageService.addProduct(newProduct, user);
    }

    @Test
    public void addProduct_ShouldInvokeRestTemplateOnce() throws Exception {
        category.setId(0);
        unit.setId(0);
        product.setCategory(category);
        product.setUnit(unit);
        productPageService.addProduct(product, user);
        verify(restTemplate, times(1)).postForObject(anyString(), eq(product), eq(Product.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void editProduct() throws Exception {
        productPageService.editProduct(product, user);
        verify(restTemplate, times(1)).put(anyString(), eq(product), eq(Product.class));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Category.class), anyMap());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Unit.class), anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void delProduct() throws Exception {
        productPageService.delProduct(1);
        verify(restTemplate, times(1)).delete(anyString(), anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getAllStores() throws Exception {
        List<Store> stores = Arrays.asList(store, store);
        ResponseEntity<List<Store>> storesResponse = new ResponseEntity<>(stores, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Store>>() {}),
                anyMap())
        ).thenReturn(storesResponse);
        assertEquals(stores,productPageService.getAllStores(1));
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Store>>() {}),
                anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getAllStoresId_ShouldReturnMapWithIdNameAddressOfStores() throws Exception {
        Map<Integer, String> allStoresById = new HashMap<>();
        store.setId(1);
        store.setName("name");
        store.setAddress("address");
        Store store1 = new Store();
        store1.setId(2);
        store1.setName("name1");
        store1.setAddress("address1");
        List<Store> stores = Arrays.asList(store, store1);
        allStoresById.put(store.getId(), store.getName() + ", " + store.getAddress());
        allStoresById.put(store1.getId(), store1.getName() + ", " + store1.getAddress());
        ResponseEntity<List<Store>> storesResponse = new ResponseEntity<>(stores, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Store>>() {}),
                anyMap())
        ).thenReturn(storesResponse);

        assertEquals(allStoresById,productPageService.getAllStoresId(1));
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                eq(new ParameterizedTypeReference<List<Store>>() {}),
                anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getStoresInProduct_ShouldReturnStoresInProduct() throws Exception {
        store.setId(1);
        Store store1 = new Store();
        store1.setId(2);
        List<Store> stores = Arrays.asList(store, store1);
        product.setStores(stores);
        List<Integer> listStoresInProductById = new ArrayList<>();
        listStoresInProductById.add(store.getId());
        listStoresInProductById.add(store1.getId());
        StoresInProduct storesInProduct = new StoresInProduct();
        storesInProduct.setStoresId(listStoresInProductById);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(product);

        assertEquals(storesInProduct,productPageService.getStoresInProduct(1));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getStoresInProduct_ShouldReturnEmptyStoresInProduct() throws Exception {
        product.setStores(null);
        List<Integer> listStoresInProductById = new ArrayList<>();
        StoresInProduct storesInProduct = new StoresInProduct();
        storesInProduct.setStoresId(listStoresInProductById);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(product);

        assertEquals(storesInProduct,productPageService.getStoresInProduct(1));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void updateStoresInProduct_ShouldCallTwoPostMethods() throws Exception {
        store.setId(1);
        Store store1 = new Store();
        store1.setId(2);
        List<Store> oldStores = Arrays.asList(store, store1);
        Store store2 = new Store();
        store2.setId(3);
        StoresInProduct storesInProduct = new StoresInProduct();
        List<Integer> listStoresInProductById = new ArrayList<>();
        listStoresInProductById.add(store.getId());
        listStoresInProductById.add(store2.getId());
        storesInProduct.setStoresId(listStoresInProductById);
        product.setStores(oldStores);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(product);

        productPageService.updateStoresInProduct(storesInProduct,1);

        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verify(restTemplate,times(2)).getForObject(anyString(), eq(Store.class), anyMap());
        verify(restTemplate,times(2)).postForObject(anyString(), eq(product), eq(Product.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void updateStoresInProduct_ShouldCallOnePostMethod() throws Exception {
        StoresInProduct storesInProduct = new StoresInProduct();
        List<Integer> listStoresInProductById = new ArrayList<>();
        listStoresInProductById.add(store.getId());
        storesInProduct.setStoresId(listStoresInProductById);
        product.setStores(null);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(product);

        productPageService.updateStoresInProduct(storesInProduct,1);

        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verify(restTemplate,times(1)).getForObject(anyString(), eq(Store.class), anyMap());
        verify(restTemplate,times(1)).postForObject(anyString(), eq(product), eq(Product.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void getProductByNameAndUserId() throws Exception {
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(product);
        assertEquals(product,productPageService.getProductByNameAndUserId(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isAlreadyExist_ShouldReturnTrue() throws Exception {
        Product expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setEnabled(true);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(expectedProduct);

        assertEquals(true, productPageService.isAlreadyExist(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isAlreadyExist_ShouldReturnFalseWhenSameId() throws Exception {
        Product expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setEnabled(true);
        product.setId(1);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(expectedProduct);

        assertEquals(false, productPageService.isAlreadyExist(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isAlreadyExist_ShouldReturnFalseWhenProductIsNull() throws Exception {
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(null);

        assertEquals(false, productPageService.isAlreadyExist(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isAlreadyExist_ShouldReturnFalseWhenProductIsDeleted() throws Exception {
        Product expectedProduct = new Product();
        expectedProduct.setId(1);
        expectedProduct.setEnabled(false);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(expectedProduct);

        assertEquals(false, productPageService.isAlreadyExist(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isDeleted_ShouldReturnTrue() throws Exception {
        Product expectedProduct = new Product();
        expectedProduct.setEnabled(false);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(expectedProduct);

        assertEquals(true, productPageService.isDeleted(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isDeleted_ShouldReturnFalseWhenProductIsEnabled() throws Exception {
        Product expectedProduct = new Product();
        expectedProduct.setId(2);
        expectedProduct.setEnabled(true);
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(expectedProduct);

        assertEquals(false, productPageService.isDeleted(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void isDeleted_ShouldReturnFalseWhenProductIsNull() throws Exception {
        when(restTemplate.getForObject(anyString(),eq(Product.class),anyMap())).thenReturn(null);

        assertEquals(false, productPageService.isDeleted(product,user));
        verify(restTemplate,times(1)).getForObject(anyString(),eq(Product.class),anyMap());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void restoreProduct() throws Exception {
        productPageService.restoreProduct(product);

        verify(restTemplate,times(1)).put(anyString(), eq(product), eq(Product.class));
        verifyNoMoreInteractions(restTemplate);
    }

}