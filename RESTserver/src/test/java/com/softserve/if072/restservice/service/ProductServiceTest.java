package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * The ProductServiceTest class is used to test ProductService class methods
 *
 * @author Vitaliy Malisevych
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Store store;
    private List<Store> stores;

    @Before
    public void setup() {
        product = new Product();
        product.setId(1);
        product.setName("name");
        product.setDescription("description");
        store = new Store();
        store.setEnabled(true);
        stores = Arrays.asList(store, store);
        product.setStores(stores);
    }

    @Test
    public void testGetProductById_ShouldReturnProduct() {
        when(productDAO.getByID(anyInt())).thenReturn(product);

        assertTrue(product.equals(productService.getProductById(anyInt())));
        verify(productDAO, times(1)).getByID(anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test(expected = NullPointerException.class)
    public void testGetProductById_ShouldThrowException() {
        when(productDAO.getByID(anyInt())).thenReturn(null);

        productService.getProductById(anyInt());

        verify(productDAO, times(1)).getByID(anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void testAddProduct() {
        productService.addProduct(product);

        verify(productDAO, times(1)).insert(product);
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void testUpdateProduct() {
        when(productDAO.getByID(anyInt())).thenReturn(product);

        productService.updateProduct(product);

        verify(productDAO, times(1)).getByID(anyInt());
        verify(productDAO, times(1)).update(product);
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void testUpdateProductByImage() {
        when(productDAO.getByID(anyInt())).thenReturn(product);

        productService.updateProductByImage(product);

        verify(productDAO, times(1)).getByID(anyInt());
        verify(productDAO, times(1)).updateImage(product);
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void deleteProduct() {
        when(productDAO.getByID(anyInt())).thenReturn(product);

        productService.deleteProduct(anyInt());

        verify(productDAO, times(1)).getByID(anyInt());
        verify(productDAO, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void getStoresByProductId_ShouldReturnListOfStores() {
        when(productDAO.getStoresByProductIdAndUserId(anyInt(), anyInt())).thenReturn(stores);

        assertEquals(stores, productService.getStoresByProductId(anyInt(), anyInt()));
        verify(productDAO, times(1)).getStoresByProductIdAndUserId(anyInt(), anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void deleteStoreFromProductById() {
        when(productDAO.getStoreFromProductById(anyInt(), anyInt())).thenReturn(store);

        productService.deleteStoreFromProductById(product);

        verify(productDAO, times(2)).getStoreFromProductById(anyInt(), anyInt());
        verify(productDAO, times(2)).deleteStoreFromProductById(anyInt(), anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void addStoresToProduct() {
        productService.addStoresToProduct(product);

        verify(productDAO, times(2)).addStoreToProduct(anyInt(), anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void getProductByNameAndUserId_ShouldReturnProduct() {
        when(productDAO.getProductByNameAndUserId(anyString(), anyInt())).thenReturn(product);

        assertEquals(product, productService.getProductByNameAndUserId(anyString(), anyInt()));
        verify(productDAO, times(1)).getProductByNameAndUserId(anyString(), anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void restoreProduct() {
        when(productDAO.getByID(anyInt())).thenReturn(product);

        productService.restoreProduct(product);

        verify(productDAO, times(1)).getByID(anyInt());
        verify(productDAO, times(1)).restore(eq(product));
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void getUserIdByProductId() {
        int userId = 1;
        when(productDAO.getUserIdByProductId(anyInt())).thenReturn(userId);

        assertEquals(userId, productService.getUserIdByProductId(anyInt()));
        verify(productDAO, times(1)).getUserIdByProductId(anyInt());
        verifyNoMoreInteractions(productDAO);
    }

}
