package com.gp.beershop.controller;

import com.gp.beershop.mock.BeerMock;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    public void testBeerGetAll() throws Exception {
        mockMvc.perform(get("/api/beer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"type\": \"светлое\",\n" +
                        "        \"inStock\": true,\n" +
                        "        \"name\": \"Лидское\",\n" +
                        "        \"description\": \"Лучшее пиво по бабушкиным рецептам\",\n" +
                        "        \"alcohol\": 5.0,\n" +
                        "        \"density\": 11.5,\n" +
                        "        \"country\": \"Республика Беларусь\",\n" +
                        "        \"price\": 5.0\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"type\": \"темное\",\n" +
                        "        \"inStock\": true,\n" +
                        "        \"name\": \"Аливария\",\n" +
                        "        \"description\": \"Пиво номер 1 в Беларуси\",\n" +
                        "        \"alcohol\": 4.6,\n" +
                        "        \"density\": 10.2,\n" +
                        "        \"country\": \"Республика Беларусь\",\n" +
                        "        \"price\": 3.0\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    @Order(2)
    public void testBeerFilter() throws Exception {
        mockMvc.perform(get("/api/beer")
                .param("type", "темное")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"type\": \"темное\",\n" +
                        "        \"inStock\": true,\n" +
                        "        \"name\": \"Аливария\",\n" +
                        "        \"description\": \"Пиво номер 1 в Беларуси\",\n" +
                        "        \"alcohol\": 4.6,\n" +
                        "        \"density\": 10.2,\n" +
                        "        \"country\": \"Республика Беларусь\",\n" +
                        "        \"price\": 3.0\n" +
                        "    }\n" +
                        "]"));
    }

    @Test
    @Order(3)
    public void testNewBeer() throws Exception {
        mockMvc.perform(post("/api/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"type\": \"светлое осветлённое\",\n" +
                        "    \"inStock\": true,\n" +
                        "    \"name\": \"Pilsner Urquell\",\n" +
                        "    \"description\": \"непастеризованное\",\n" +
                        "    \"alcohol\": 4.2,\n" +
                        "    \"density\": 12.0,\n" +
                        "    \"country\": \"Чехия\",\n" +
                        "    \"price\": 8.0\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "  \"id\" : 3\n" +
                        "}"));
    }

    @Test
    @Order(4)
    public void testUpdateBeerById() throws Exception {
        mockMvc.perform(patch("/api/beer/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"price\": 8.30\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\" : 3\n" +
                        "}"));
    }

    @Test
    public void testDeleteBeerById() throws Exception {
        mockMvc.perform(delete("/api/beer/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\" : 3\n" +
                        "}"));
    }
}
