package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.repository.BeerRepository;
import mock.BeerMock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BeerControllerTest extends AbstractControllerTest {
    @MockBean
    private BeerRepository beerRepository;
    @SpyBean
    private BeerMapper beerMapper;

    @Test
    public void testBeerGetAll() throws Exception {
        // given
        willReturn(List.of(BeerMock.getById(LIDSKOE), BeerMock.getById(ALIVARIA))
                       .stream()
                       .map(beerMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(beerRepository).findAll();
        //when
        mockMvc.perform(get("/api/beers")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(List.of(BeerMock.getById(LIDSKOE), BeerMock.getById(ALIVARIA)))));
        verify(beerRepository, times(1)).findAll();
    }

    @Test
    public void testBeerFilter() throws Exception {
        // given
        willReturn(List.of(BeerMock.getById(LIDSKOE), BeerMock.getById(ALIVARIA))
                       .stream()
                       .map(beerMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(beerRepository).findAll();
        // when
        mockMvc.perform(get("/api/beers")
                            .param("type", "темное")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(BeerMock.getById(ALIVARIA)))));
        verify(beerRepository, times(1)).findAll();
    }

    @Test
    public void testNewBeer() throws Exception {
        //given
        final String token = signInAsUser(true);
        final Beer beer = BeerMock.getById(PILSNER);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);

        willReturn(beerEntity).given(beerRepository).save(any(BeerEntity.class));
        // when
        mockMvc.perform(post("/api/beers")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(beer)))
            // then
            .andExpect(status().isCreated())
            .andExpect(content().json(mapper.writeValueAsString(PILSNER)));
        verify(beerRepository, times(1)).save(any(BeerEntity.class));
    }

    @Test
    public void testNewBeer_BeerAlreadyExists() throws Exception {
        //given
        final String token = signInAsUser(true);
        final Beer beer = BeerMock.getById(PILSNER);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);
        willReturn(Optional.of(beerEntity)).given(beerRepository).findFirstByName(beerEntity.getName());
        // when
        mockMvc.perform(post("/api/beers")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(beer)))
            // then
            .andExpect(status().isConflict());
        verify(beerRepository, times(1)).findFirstByName(beerEntity.getName());
    }

    @Test
    public void testUpdateBeerById() throws Exception {
        // given
        final String token = signInAsUser(true);
        final Beer beer = BeerMock.getById(PILSNER);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beer);
        willReturn(Optional.of(beerEntity)).given(beerRepository).findById(PILSNER);
        // when
        mockMvc.perform(put("/api/beers/" + PILSNER)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                BeerMock.getById(5L))))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(BeerMock.getById(5L))));
        verify(beerRepository, times(1)).findById(PILSNER);
    }

    @Test
    public void testDeleteBeerById() throws Exception {
        // given
        final String token = signInAsUser(true);
        willReturn(true).given(beerRepository).existsById(PILSNER);
        // when
        mockMvc.perform(delete("/api/beers/" + PILSNER)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());
        verify(beerRepository, times(1)).existsById(PILSNER);
    }

    @Test
    public void testDeleteBeerById_BeerNotExists() throws Exception {
        // given
        final String token = signInAsUser(true);
        willReturn(false).given(beerRepository).existsById(PILSNER);
        // when
        mockMvc.perform(delete("/api/beers/" + PILSNER)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No beer with id = " +
                                      PILSNER + " was found.\"}"));
        verify(beerRepository, times(1)).existsById(PILSNER);
    }

}
