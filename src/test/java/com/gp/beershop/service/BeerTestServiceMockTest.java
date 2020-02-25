package com.gp.beershop.service;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.mapper.BeerMapperImpl;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
public class BeerTestServiceMockTest {
    private static final int ID = 1;

    @Mock
    private BeerRepository beerRepository;
    @InjectMocks
    private BeerService beerService;
    @Spy
    private BeerMapperImpl beerMapper;

    @Test
    public void testGetUserById() throws NoSuchBeerException {
        final Beer beerExpected = BeerMock.getById(ID);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beerExpected);
        willReturn(true).given(beerRepository).existsById(ID);
        Mockito.doReturn(Optional.of(beerEntity)).when(beerRepository).findById(ID);

        final Beer beerActual = beerService.getBeerById(ID);
        assertEquals(beerExpected, beerActual);
    }

    @Test
    public void testNoSuchBeerException() {
        Mockito.doReturn(false).when(beerRepository).existsById(ID);
        assertThrows(NoSuchBeerException.class, () -> beerService.deleteBeerById(ID));
    }
}
