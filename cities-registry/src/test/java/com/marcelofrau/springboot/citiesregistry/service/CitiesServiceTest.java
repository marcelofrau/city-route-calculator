package com.marcelofrau.springboot.citiesregistry.service;

import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.repository.CityRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CitiesServiceTest {

    @InjectMocks
    private CitiesService service;

    @Mock
    private CityRepository repository;

    @Test
    public void shouldCallListAllCities() {
        final Iterable<City> result = Lists.emptyList();
        when(repository.findAll()).thenReturn(result);

        final Iterable<City> cities = service.listCities();
        assertNotNull(cities);
        assertEquals(result, cities);

        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldCallDeleteCity() {
        final City city = new City();
        final City deletedCity = service.deleteCity(city);

        assertEquals(city, deletedCity);
        verify(repository, times(1)).delete(any());
    }

    @Test
    public void shouldCallSaveCity() {
        final City city = new City("Mock City", "ST");
        when(repository.save(any())).thenReturn(city);

        final City savedCity = service.saveCity(city);
        assertEquals(city, savedCity);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldCallFindCityById() {
        final City city = new City("Mock City", "ST");
        when(repository.findById(anyLong())).thenReturn(Optional.of(city));

        final Optional<City> foundCity = service.findCity(1L);
        assert(foundCity.isPresent());

        assertEquals(city, foundCity.get());

        verify(repository, times(1)).findById(any());
    }

    @Test
    public void shouldCallFindCityByName() {
        final City city = new City("Mock City", "ST");
        when(repository.findByName(anyString())).thenReturn(Lists.list(city));

        final Iterable<City> foundCity = service.findCity("Mock City");
        assert(foundCity.iterator().hasNext());

        assertEquals(city, foundCity.iterator().next());
        verify(repository, times(1)).findByName(any());
    }

}
