package com.egemsoft.rickandmorty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public abstract class AbstractBaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    public AbstractBaseControllerTest() {
    }

    protected MockMvc getMockMvc() {
        return this.mockMvc;
    }

    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
