package com.marcelofrau.springboot.citiesregistry.controller;

import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.repository.CityRepository;
import com.marcelofrau.springboot.citiesregistry.service.CitiesService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CitiesControllerTest {

    @InjectMocks
    private CitiesController controller;

    @Mock
    private CitiesService service;

    @Mock
    private CityRepository repository;

    @Test
    public void shouldListCities() {
        final Iterable<City> cities = Lists.list(new City("Mock", "ST"));
        when(service.listCities()).thenReturn(cities);

        final ResponseEntity<Iterable<City>> responseEntity = controller.listCities();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        final Iterable<City> listedCities = responseEntity.getBody();
        assertNotNull(listedCities);
        assertEquals(cities, listedCities);

        verify(service, times(1)).listCities();
    }

    @Test
    public void shouldSaveCity() {
        final City city = getCity();
        when(service.saveCity(any())).thenReturn(city);

        final City newCity = new City();
        final ResponseEntity<City> responseEntity = controller.saveCity(newCity);

        assertNotNull(responseEntity);
        assertEquals(city, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(service, times(1)).saveCity(any());
    }

    @Test
    public void shouldDeleteCity() {
        final City city = getCity();
        when(service.deleteCity(any())).thenReturn(city);
        when(service.findCity(anyLong())).thenReturn(Optional.of(city));

        final ResponseEntity<City> responseEntity = controller.deleteCity(city);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(city, responseEntity.getBody());

        verify(service, times(1)).deleteCity(any());
    }

    @Test
    public void shouldNotDeleteCity() {
        final City city = getCity();
        when(service.deleteCity(any())).thenReturn(city);
        when(service.findCity(anyLong())).thenReturn(Optional.empty());

        final ResponseEntity<City> responseEntity = controller.deleteCity(city);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(service, times(1)).deleteCity(any());
    }

//    public ResponseEntity<City> deleteCity(@RequestBody City city) {
//    public ResponseEntity<Iterable<City>> findCity(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {


    private City getCity() {
        final City city = new City();
        city.setId(10L);
        city.setName("Name");
        city.setName("State");
        return city;
    }


}
