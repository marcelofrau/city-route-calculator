package com.marcelofrau.springboot.routecalculator.service;

import com.marcelofrau.springboot.routecalculator.model.City;
import com.marcelofrau.springboot.routecalculator.model.CityConnection;
import com.marcelofrau.springboot.routecalculator.model.response.RouteResponse;
import com.marcelofrau.springboot.routecalculator.service.utils.CityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RouteCalculatorServiceTest {

    @InjectMocks
    private RouteCalculatorService service;

    @Mock
    private RestTemplate restTemplate;

    private City[] cities;
    private CityConnection[] connections;

    @Test(expected = CityNotFoundException.class)
    public void shouldNotFindFromCity() {
        when(restTemplate.getForEntity("null/cities", City[].class)).thenReturn(new ResponseEntity<>(new City[0], HttpStatus.OK));
        when(restTemplate.getForEntity("null/connections", CityConnection[].class)).thenReturn(new ResponseEntity<>(new CityConnection[0], HttpStatus.OK));

        service.calculateRoute("foo", "bar");
    }

    @Test(expected = CityNotFoundException.class)
    public void shouldNotFindToCity() {
        when(restTemplate.getForEntity("null/cities", City[].class)).thenReturn(new ResponseEntity<>(new City[] {
                new City("foo", "ST")
        }, HttpStatus.OK));
        when(restTemplate.getForEntity("null/connections", CityConnection[].class)).thenReturn(new ResponseEntity<>(new CityConnection[0], HttpStatus.OK));

        service.calculateRoute("foo", "bar");
    }

    @Test
    public void shouldNotCalculateRoute() {
        when(restTemplate.getForEntity("null/cities", City[].class)).thenReturn(new ResponseEntity<>(cities, HttpStatus.OK));
        when(restTemplate.getForEntity("null/connections", CityConnection[].class)).thenReturn(new ResponseEntity<>(connections, HttpStatus.OK));

        final Optional<RouteResponse> routeResponse = service.calculateRoute("Unconnected", "Sao Paulo");

        assertFalse(routeResponse.isPresent());

        verify(restTemplate, times(2)).getForEntity(anyString(), any());
    }

    @Test
    public void shouldCalculateRoute() {
        when(restTemplate.getForEntity("null/cities", City[].class)).thenReturn(new ResponseEntity<>(cities, HttpStatus.OK));
        when(restTemplate.getForEntity("null/connections", CityConnection[].class)).thenReturn(new ResponseEntity<>(connections, HttpStatus.OK));

        final String fromCityName = "Campinas";
        final String toCityName = "Sao Paulo";
        final Optional<RouteResponse> routeResponse = service.calculateRoute(fromCityName, toCityName);

        assert(routeResponse.isPresent());

        final RouteResponse response = routeResponse.get();
        assertEquals(fromCityName, response.getOriginCity().getName());
        assertEquals(toCityName, response.getDestinationCity().getName());
        assertEquals(Integer.valueOf(1), response.getMinimumPathInConnections().getCount());
        assertEquals(Integer.valueOf(1), response.getMinimumPathInTime().getCount());

        verify(restTemplate, times(2)).getForEntity(anyString(), any());
    }

    @Test
    public void shouldCalculateRoute2() {
        when(restTemplate.getForEntity("null/cities", City[].class)).thenReturn(new ResponseEntity<>(cities, HttpStatus.OK));
        when(restTemplate.getForEntity("null/connections", CityConnection[].class)).thenReturn(new ResponseEntity<>(connections, HttpStatus.OK));

        final String fromCityName = "Campinas";
        final String toCityName = "Maceio";
        final Optional<RouteResponse> routeResponse = service.calculateRoute(fromCityName, toCityName);

        assert(routeResponse.isPresent());

        final RouteResponse response = routeResponse.get();
        assertEquals(fromCityName, response.getOriginCity().getName());
        assertEquals(toCityName, response.getDestinationCity().getName());
        assertEquals(Integer.valueOf(1), response.getMinimumPathInConnections().getCount());
        assertEquals(Integer.valueOf(3), response.getMinimumPathInTime().getCount());

        verify(restTemplate, times(2)).getForEntity(anyString(), any());
    }


    @Before
    public void initializeData() {
        final City campinas = new City(1L, "Campinas", "SP");
        final City saoPaulo = new City(2L, "Sao Paulo", "SP");
        final City guaratingueta = new City(3L, "Guaratingueta", "SP");
        final City rioDeJaneiro = new City(4L, "Rio de Janeiro", "RJ");
        final City patoBranco = new City(5L, "Pato Branco", "RS");
        final City campinaGrande = new City(6L, "Campina Grande", "PB");
        final City salvador = new City(7L, "Salvador", "BA");
        final City maceio = new City(8L, "Maceio", "PA");
        final City curitiba = new City(9L,"Curitiba", "PR");
        final City unconnectedCity = new City(10L, "Unconnected", "PR");
        final City unconnected1City = new City(11L, "Unconnected 1", "PR");
        final City unconnected2City = new City(12L, "Unconnected 2", "PR");

        this.cities = new City[] {campinas, saoPaulo, guaratingueta, rioDeJaneiro, patoBranco, campinaGrande, salvador, maceio, curitiba,
                unconnected1City, unconnected2City, unconnectedCity
        };

        this.connections = new CityConnection[] {
                new CityConnection(1L, campinas, saoPaulo, 1d),
                new CityConnection(2L, saoPaulo, rioDeJaneiro, 5d),
                new CityConnection(3L, saoPaulo, maceio, 8d),
                new CityConnection(4L, campinas, maceio, 16d),
                new CityConnection(5L, rioDeJaneiro, maceio, 2d),
                new CityConnection(6L, saoPaulo, guaratingueta, 4d),
                new CityConnection(7L, saoPaulo, salvador, 50d),
                new CityConnection(8L, saoPaulo, campinaGrande, 80d),
                new CityConnection(9L, campinas, campinaGrande, 8d),
                new CityConnection(10L, patoBranco, guaratingueta, 4d),
                new CityConnection(11L, curitiba, salvador, 50d),
                new CityConnection(12L, patoBranco, saoPaulo, 32d),
                new CityConnection(13L, curitiba, saoPaulo, 24d),
                new CityConnection(14L, campinas, curitiba, 24d),
                new CityConnection(15L, unconnected1City, unconnected2City, 24d)
        };
    }




}
