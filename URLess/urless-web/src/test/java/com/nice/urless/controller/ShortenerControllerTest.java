package com.nice.urless.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.urless.ServiceConfigurations;
import com.nice.urless.dto.CreateURLRequest;
import com.nice.urless.dto.CreateURLResponse;
import gateway.URLGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shortener.ShortenURL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ShortenerControllerTest.class, ServiceConfigurations.class, ShortenerController.class})
@AutoConfigureMockMvc
public class ShortenerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private URLGateway urlGateway;

    @Test
    public void shouldReturnNotfoundOnNonExistingURL() throws Exception {
        mockMvc.perform(get("/NON_EXISTING-URL"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string("URL does not exists"));
    }

    @Test
    public void shouldReturn301OnExistingURL() throws Exception {
        //arrange
        urlGateway.create("http://some.test", "abcde12");
        //act and assert
        mockMvc.perform(get("/abcde12"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", "http://some.test"));
    }

    @Test
    public void shouldReturn201OnCreatingURL() throws Exception {
        //arrange
        ObjectMapper objectMapper = new ObjectMapper();
        CreateURLRequest request = new CreateURLRequest("http://some.url");
        String json = objectMapper.writeValueAsString(request);
        //act
        MvcResult result = mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn();
        CreateURLResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), CreateURLResponse.class);
        ShortenURL expected = urlGateway.getAll().get(0);
        //assert
        assertEquals(expected.getUrl(), response.getOriginalUrl());
        assertEquals("http://urle.ss" + expected.getId(), response.getUrl());

    }


}
