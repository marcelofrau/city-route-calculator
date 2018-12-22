package com.marcelofrau.springboot.citiesregistry.service;

import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import com.marcelofrau.springboot.citiesregistry.repository.CityConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the main service for all CityConnection finding/registering
 * information.
 *
 * In this class you can create, remove, list and even find
 * all connections registered in this micro-service.
 *
 * @see CityConnection
 */
@Service
public class ConnectionsService {

    private final CityConnectionRepository repository;

    public ConnectionsService(@Autowired CityConnectionRepository cityTimeConnectionRepository) {
        this.repository = cityTimeConnectionRepository;
    }

    /**
     * List all connections registered
     * @return Iterable of all city connections
     */
    public Iterable<CityConnection> listConnections() {
        return repository.findAll();
    }

    /**
     * Saves a connection on the registry.
     *
     * If the connection is with null id, it will be created
     * and if the connection already contain an Id, it will
     * be updated.
     *
     * @param connection City to be inserted/saved
     * @return The city containing the id filled if it was just created.
     */
    public CityConnection saveConnection(CityConnection connection) {
        return repository.save(connection);
    }

    /**
     * Delete a CityConnection from the registry.
     * @param connection City to be removed
     * @return The removed connection
     */
    public CityConnection deleteConnection(CityConnection connection) {
        repository.delete(connection);
        return connection;
    }

    /**
     * Finds a connection by an Id
     * @param id Id of a connection.
     * @return A connection with the given Id, if no city is found, it will return null.
     */
    public Optional<CityConnection> findConnection(Long id) {
        return repository.findById(id);
    }
}
