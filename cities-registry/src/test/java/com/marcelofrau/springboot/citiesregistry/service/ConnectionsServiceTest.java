package com.marcelofrau.springboot.citiesregistry.service;

import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import com.marcelofrau.springboot.citiesregistry.repository.CityConnectionRepository;
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
public class ConnectionsServiceTest {

    @InjectMocks
    private ConnectionsService service;

    @Mock
    private CityConnectionRepository repository;

    @Test
    public void shouldCallListConnections() {
        final Iterable<CityConnection> connections = Lists.emptyList();
        when(repository.findAll()).thenReturn(connections);

        final Iterable<CityConnection> cityConnections = service.listConnections();
        assertNotNull(cityConnections);
        assertEquals(connections, cityConnections);

        verify(repository, times(1)).findAll();
    }

    @Test
    public void shouldCallSaveConnection() {
        final CityConnection connection = new CityConnection();
        when(repository.save(connection)).thenReturn(connection);

        final CityConnection savedConnection = service.saveConnection(connection);
        assertNotNull(savedConnection);
        assertEquals(connection, savedConnection);

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldCallDeleteConnection() {
        final CityConnection connection = new CityConnection();

        final CityConnection deletedConnection = service.deleteConnection(connection);
        assertEquals(connection, deletedConnection);

        verify(repository, times(1)).delete(any());
    }

    @Test
    public void shouldCallFindConnectionById() {
        final CityConnection connection = new CityConnection();
        when(repository.findById(any())).thenReturn(Optional.of(connection));

        final Optional<CityConnection> foundConnection = service.findConnection(1L);
        assertNotNull(foundConnection);
        assert(foundConnection.isPresent());
        assertEquals(connection, foundConnection.get());

        verify(repository, times(1)).findById(any());
    }

}
