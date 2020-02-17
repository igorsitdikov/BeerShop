package com.gp.beershop.mapper;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.service.BeerService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@SpringBootTest()
@TestPropertySource("classpath:application-test.properties")
@Log
public class BeerTestMapperMockTest {
    private static final int ID = 1;
    @MockBean
    protected BeerRepository beerRepository;
    @SpyBean
    private BeerService beerService;
    @SpyBean
    private BeerMapper beerMapper;

    @Test
    public void testConverterBeerDtoToBeerEntity() {
        final Beer beerDto = BeerMock.getById(ID);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beerDto);

        assertEquals(beerDto.getId(), beerEntity.getId());
        assertEquals(beerDto.getType(), beerEntity.getType());
        assertEquals(beerDto.getInStock(), beerEntity.getInStock());
        assertEquals(beerDto.getName(), beerEntity.getName());
        assertEquals(beerDto.getDescription(), beerEntity.getDescription());
        assertEquals(beerDto.getAlcohol(), beerEntity.getAlcohol());
        assertEquals(beerDto.getDensity(), beerEntity.getDensity());
        assertEquals(beerDto.getCountry(), beerEntity.getCountry());
        assertEquals(beerDto.getPrice(), beerEntity.getPrice());
    }

    @Test
    public void testConverterBeerEntityToBeerDto() {
        final Beer beerDto = BeerMock.getById(ID);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beerDto);

        doReturn(Optional.of(beerEntity)).when(beerRepository).findById(ID);

        final Beer beer = beerService.getBeerById(ID);

        assertEquals(beerEntity.getId(), beer.getId());
        assertEquals(beerEntity.getType(), beer.getType());
        assertEquals(beerEntity.getInStock(), beer.getInStock());
        assertEquals(beerEntity.getName(), beer.getName());
        assertEquals(beerEntity.getDescription(), beer.getDescription());
        assertEquals(beerEntity.getAlcohol(), beer.getAlcohol());
        assertEquals(beerEntity.getDensity(), beer.getDensity());
        assertEquals(beerEntity.getCountry(), beer.getCountry());
        assertEquals(beerEntity.getPrice(), beer.getPrice());
    }
}
