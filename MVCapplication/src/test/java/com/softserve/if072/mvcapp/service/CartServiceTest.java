package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.dto.CartDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * The CartServiceTest class is used to test CartService class methods
 *
 * @author Igor Kryviuk
 */
public class CartServiceTest {
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void getByUserId() throws Exception {
        this.mockServer.expect(requestTo("/rest/api/users/4/carts"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());
        restTemplate.getForObject("/rest/api/users/4/carts", List.class);
        mockServer.verify();
    }

    @Test
    public void productPurchase() throws Exception {
        this.mockServer.expect(requestTo("/rest/api/users/4/carts/purchase"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess());
        restTemplate.put("/rest/api/users/4/carts/purchase", new CartDTO());
        mockServer.verify();
    }

    @Test
    public void deleteProductFromCart() throws Exception {
        this.mockServer.expect(requestTo("/rest/api/users/4/carts/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        restTemplate.delete("/rest/api/users/4/carts/1");
        mockServer.verify();
    }
}