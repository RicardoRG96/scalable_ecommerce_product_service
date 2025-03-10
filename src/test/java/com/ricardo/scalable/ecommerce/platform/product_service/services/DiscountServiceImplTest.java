package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;

@SpringBootTest
public class DiscountServiceImplTest {

    @MockitoBean
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountService discountService;

    

}
