package com.gp.beershop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.beershop.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @SpyBean
    protected BeerService beerService;

    @Autowired
    protected ObjectMapper mapper;

}
