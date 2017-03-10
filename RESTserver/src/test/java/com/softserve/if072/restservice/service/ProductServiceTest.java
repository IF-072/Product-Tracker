package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.restservice.dao.mybatisdao.ProductDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

    @Before
    public void setup() {
        product = new Product();
        product.setId(1);
        product.setName("name");
        product.setDescription("description");
    }

    @Test
    public void testGetProductById_ShouldReturnProduct() {
        when(productDAO.getByID(anyInt())).thenReturn(product);
        assertTrue(product.equals(productService.getProductById(anyInt())));
        verify(productDAO, times(1)).getByID(anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetProductById_ShouldThrowException() {
        productService.getAllProducts(anyInt());
        verify(productDAO, times(1)).getByID(anyInt());
        verifyNoMoreInteractions(productDAO);
    }

    @Test
    public void testAddProduct() {
        productService.addProduct(product);
        verify(productDAO, times(1)).insert(product);
    }

    @Test
    public void testUpdateProduct() {
        productService.updateProduct(product);
        verify(productDAO, times(1)).update(product);
    }

    @Test
    public void testupdateProductByImage() {
        productService.updateProductByImage(product);
        verify(productDAO, times(1)).updateImage(product);
    }


}
