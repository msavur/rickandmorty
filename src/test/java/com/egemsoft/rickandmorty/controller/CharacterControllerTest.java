package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.RickMortyApplication;
import com.egemsoft.rickandmorty.controller.endpoint.CharacterEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RickMortyApplication.class)
public class CharacterControllerTest extends AbstractBaseControllerTest {

    @Test
    public void shouldSuccessGetAllCharacter() throws Exception {
        MockHttpServletResponse response = getMockMvc()
                .perform(MockMvcRequestBuilders.get(CharacterEndpoint.GET_ALL_CHARACTER)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        GenericResponse genericResponse = getObjectMapper().readValue(response.getContentAsString(), GenericResponse.class);
        assertNotNull(genericResponse);
    }

    @Test
    public void shouldSuccessGetCharacter() throws Exception {
        MockHttpServletResponse response = getMockMvc()
                .perform(MockMvcRequestBuilders.get(CharacterEndpoint.GET_CHARACTER, 1)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        GenericResponse genericResponse = getObjectMapper().readValue(response.getContentAsString(), GenericResponse.class);
        assertNotNull(genericResponse);
    }

}

