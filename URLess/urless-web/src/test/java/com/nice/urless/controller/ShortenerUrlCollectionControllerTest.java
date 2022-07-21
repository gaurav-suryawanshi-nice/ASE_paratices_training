package com.nice.urless.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.urless.ServiceConfigurations;
import com.nice.urless.dto.CreateURLCollectionRequest;
import com.nice.urless.dto.CreateURLRequest;
import com.nice.urless.dto.CreateURLResponse;
import gateway.URLCollectionGateway;
import gateway.URLGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import shortener.ShortenURL;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ShortenerUrlCollectionControllerTest.class, ServiceConfigurations.class, ShortenerController.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class ShortenerUrlCollectionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private URLCollectionGateway urlCollectionGateway;

    @Test
    public void shouldReturnNotfoundOnNonExistingURL() throws Exception {
        mockMvc.perform(get("/NON_EXISTING-URL_COLLECATION_ID"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .string("URL does not exists"));
    }

    @Test
    public void shouldReturn301OnExistingURL() throws Exception {
        //arrange
        List<String> urls = Arrays.asList("http://some.test");
        urlCollectionGateway.create(urls, "abcd1");
        //act and assert
        mockMvc.perform(get("/abcd1"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", "http://some.test"));
    }

    @Test
    public void shouldReturn201OnCreatingURL() throws Exception {
        //arrange
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> urlCollection = Arrays.asList("http://some.url");
        CreateURLCollectionRequest request = new CreateURLCollectionRequest(urlCollection);
        String json = objectMapper.writeValueAsString(request);
        //act
        MvcResult result = mockMvc.perform(post("/collection").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn();
        List<CreateURLResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CreateURLResponse>>() {
        });
        Map<String, List<ShortenURL>> expected = urlCollectionGateway.getAll();
        //assert
        assertEquals(expected.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).get(0).getUrl(), response.get(0).getUrl());

    }
}
