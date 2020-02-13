package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.mock.BeerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BeerControllerTest extends AbstractControllerTest {

    @BeforeEach
    public void defaultSystem() {
        BeerMock.defaultState();
    }

    @Test
    public void testBeerGetAll() throws Exception {
        BeerMock.delete(3);
        mockMvc.perform(get("/api/beer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(
                        List.of(
                                Beer.builder()
                                        .id(1)
                                        .type("светлое")
                                        .inStock(true)
                                        .name("Лидское")
                                        .description("Лучшее пиво по бабушкиным рецептам")
                                        .alcohol(5.0)
                                        .density(11.5)
                                        .country("Республика Беларусь")
                                        .price(5D)
                                        .build(),
                                Beer.builder()
                                        .id(2)
                                        .type("темное")
                                        .inStock(true)
                                        .name("Аливария")
                                        .description("Пиво номер 1 в Беларуси")
                                        .alcohol(4.6)
                                        .density(10.2)
                                        .country("Республика Беларусь")
                                        .price(3D)
                                        .build()
                        )
                )));
    }

    @Test
    public void testBeerFilter() throws Exception {
        mockMvc.perform(get("/api/beer")
                .param("type", "темное")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(
                        List.of(
                                Beer.builder()
                                        .id(2)
                                        .type("темное")
                                        .inStock(true)
                                        .name("Аливария")
                                        .description("Пиво номер 1 в Беларуси")
                                        .alcohol(4.6)
                                        .density(10.2)
                                        .country("Республика Беларусь")
                                        .price(3D)
                                        .build()
                        ))));
    }

    @Test
    public void testNewBeer() throws Exception {
        BeerMock.delete(3);
        mockMvc.perform(post("/api/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        mapper.writeValueAsString(
                                Beer.builder()
                                        .type("светлое осветлённое")
                                        .inStock(true)
                                        .name("Pilsner Urquell")
                                        .description("непастеризованное")
                                        .alcohol(4.2)
                                        .density(12.0)
                                        .country("Чехия")
                                        .price(8D)
                                        .build()
                        )))
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        mapper.writeValueAsString(
                                IdResponse.builder()
                                .id(3)
                                .build()
                        )));
    }

    @Test
    public void testUpdateBeerById() throws Exception {
        mockMvc.perform(patch("/api/beer/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"price\": 8.30\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(
                                IdResponse.builder()
                                .id(3)
                                .build()
                        )
                ));
    }

    @Test
    public void testDeleteBeerById() throws Exception {
        mockMvc.perform(delete("/api/beer/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(
                                IdResponse.builder()
                                .id(3)
                                .build()
                        )
                ));
    }
}
