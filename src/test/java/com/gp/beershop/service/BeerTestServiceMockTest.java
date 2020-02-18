package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BeerTestServiceMockTest {
    private static final int ID = 1;
    @MockBean
    private BeerRepository beerRepository;
    @SpyBean
    private BeerService beerService;
    @SpyBean
    private BeerMapper beerMapper;

    @Test
    public void testGetUserById() {
        final Beer beerExpected = BeerMock.getById(ID);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beerExpected);

        Mockito.doReturn(Optional.of(beerEntity)).when(beerRepository).findById(ID);

        final Beer beerActual = beerService.getBeerById(ID);
        assertEquals(beerExpected, beerActual);
    }
}
