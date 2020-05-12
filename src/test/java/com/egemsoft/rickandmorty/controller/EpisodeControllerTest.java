package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.RickMortyApplication;
import com.egemsoft.rickandmorty.controller.endpoint.EpisodeEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RickMortyApplication.class)
public class EpisodeControllerTest extends AbstractBaseControllerTest {

    @Test
    public void shouldSuccessGetAllEpisode() throws Exception {
        MockHttpServletResponse response = getMockMvc()
                .perform(MockMvcRequestBuilders.get(EpisodeEndpoint.GET_ALL_EPISODES)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        GenericResponse genericResponse = getObjectMapper().readValue(response.getContentAsString(), GenericResponse.class);
        assertNotNull(genericResponse);
    }

    @Test
    public void shouldSuccessGetEpisode() throws Exception {
        MockHttpServletResponse response = getMockMvc()
                .perform(get(EpisodeEndpoint.GET_EPISODE, 1)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        GenericResponse genericResponse = getObjectMapper().readValue(response.getContentAsString(), GenericResponse.class);
        assertNotNull(genericResponse);
    }

}

