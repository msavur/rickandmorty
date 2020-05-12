package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.RickMortyApplication;
import com.egemsoft.rickandmorty.controller.endpoint.EpisodeEndpoint;
import com.egemsoft.rickandmorty.controller.endpoint.ReportEndpoint;
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
public class ReportControllerTest extends AbstractBaseControllerTest {

    @Test
    public void shouldSuccessGetAllReport() throws Exception {
        MockHttpServletResponse response = getMockMvc()
                .perform(MockMvcRequestBuilders.get(ReportEndpoint.GET_REPORT)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
        GenericResponse genericResponse = getObjectMapper().readValue(response.getContentAsString(), GenericResponse.class);
        assertNotNull(genericResponse);
    }
}

