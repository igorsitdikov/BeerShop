package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.repository.BeerRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log
public class BeerControllerTest extends AbstractControllerTest {
    @MockBean
    private BeerRepository beerRepository;
    @SpyBean
    private BeerMapper beerMapper;

    @Test
    public void testBeerGetAll() throws Exception {
        willReturn(List.of(
            BeerMock.getById(1),
            BeerMock.getById(2))
                       .stream()
                       .map(beerMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(beerRepository).findAll();

        mockMvc.perform(get("/api/beers")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    List.of(BeerMock.getById(1),
                            BeerMock.getById(2)))));
    }

    @Test
    public void testBeerFilter() throws Exception {
        final String token = signInAsUser(false);
        willReturn(List.of(
            BeerMock.getById(1),
            BeerMock.getById(2))
                       .stream()
                       .map(beerMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(beerRepository).findAll();

        mockMvc.perform(get("/api/beers")
                            .header("Authorization", token)
                            .param("type", "темное")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(
                List.of(BeerMock.getById(2)))));
    }

    @Test
    public void testNewBeer() throws Exception {
        final String token = signInAsUser(true);
        final Beer beer = BeerMock.getById(3);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);

        when(beerRepository.save(any(BeerEntity.class))).thenReturn(beerEntity);

        mockMvc.perform(post("/api/beers")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(beer)))
            .andExpect(status().isCreated())
            .andExpect(content().json(mapper.writeValueAsString(3)));
    }

    @Test
    public void testUpdateBeerById() throws Exception {
        final String token = signInAsUser(true);
        willReturn(
            beerMapper.sourceToDestination(BeerMock.getById(3)))
            .given(beerRepository)
            .getOne(3);
        willReturn(true)
            .given(beerRepository)
            .existsById(3);
        mockMvc.perform(put("/api/beers/3")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                BeerMock.getById(4))))
            .andExpect(status().isOk())
            .andExpect(content()
                           .json(mapper.writeValueAsString(
                               BeerMock.getById(4))));
    }

    @Test
    public void testDeleteBeerById() throws Exception {
        final String token = signInAsUser(true);
        willReturn(true).given(beerRepository).existsById(3);

        mockMvc.perform(delete("/api/beers/3")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(3)));
    }
}
