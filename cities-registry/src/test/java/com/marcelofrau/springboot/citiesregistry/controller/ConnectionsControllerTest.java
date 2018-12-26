package com.marcelofrau.springboot.citiesregistry.controller;


import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import com.marcelofrau.springboot.citiesregistry.repository.CityConnectionRepository;
import com.marcelofrau.springboot.citiesregistry.repository.CityRepository;
import com.marcelofrau.springboot.citiesregistry.service.CitiesService;
import com.marcelofrau.springboot.citiesregistry.service.ConnectionsService;
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
public class ConnectionsControllerTest {

    @InjectMocks
    private ConnectionsController controller;

    @Mock
    private ConnectionsService service;

    @Mock
    private CityConnectionRepository repository;

    @Test
    public void shouldListCities() {
        final Iterable<CityConnection> connections = Lists.list(new CityConnection(getCity(), getCity(), 10d));
        when(service.listConnections()).thenReturn(connections);

        final ResponseEntity<Iterable<CityConnection>> responseEntity = controller.listConnections();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        final Iterable<CityConnection> listedCities = responseEntity.getBody();
        assertNotNull(listedCities);
        assertEquals(connections, listedCities);

        verify(service, times(1)).listConnections();
    }

    @Test
    public void shouldSaveCity() {
        final CityConnection connection = getCityConnection();
        when(service.saveConnection(any())).thenReturn(connection);

        final CityConnection newConnection = new CityConnection();
        final ResponseEntity<CityConnection> responseEntity = controller.saveConnection(newConnection);

        assertNotNull(responseEntity);
        assertEquals(connection, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(service, times(1)).saveConnection(any());
    }

    @Test
    public void shouldDeleteCity() {
        final CityConnection connection = getCityConnection();
        when(service.deleteConnection(any())).thenReturn(connection);
        when(service.findConnection(anyLong())).thenReturn(Optional.of(connection));

        final ResponseEntity<CityConnection> responseEntity = controller.deleteConnection(connection);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(connection, responseEntity.getBody());

        verify(service, times(1)).deleteConnection(any());
    }

    @Test
    public void shouldNotDeleteCity() {
        final CityConnection connection = getCityConnection();
        when(service.findConnection(anyLong())).thenReturn(Optional.empty());

        final ResponseEntity<CityConnection> responseEntity = controller.deleteConnection(connection);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(service, times(0)).deleteConnection(any());
    }

    private City getCity() {
        final City city = new City();
        city.setId(10L);
        city.setName("Name");
        city.setName("State");
        return city;
    }

    private CityConnection getCityConnection() {
        final CityConnection connection = new CityConnection(getCity(), getCity(), 10D);
        connection.setId(-1L);
        return connection;
    }
}
