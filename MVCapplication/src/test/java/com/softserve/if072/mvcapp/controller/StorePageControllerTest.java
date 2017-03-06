package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.service.StorePageService;
import com.softserve.if072.mvcapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Nazar Vynnyk
 */
@RunWith(MockitoJUnitRunner.class)
public class StorePageControllerTest {

    @Mock
    StorePageService storePageService;
    @Mock
    UserService userService;

    @InjectMocks
    StorePageController storePageController;

    @Before
    public void setUp(){
       storePageController = new StorePageController();
    }

@Test
public void test_GetAllStoresByUserId_ShouldReturnViewName() throws Exception{


}




}
