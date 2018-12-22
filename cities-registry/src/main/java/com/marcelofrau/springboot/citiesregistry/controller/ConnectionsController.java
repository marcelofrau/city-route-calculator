package com.marcelofrau.springboot.citiesregistry.controller;

import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import com.marcelofrau.springboot.citiesregistry.service.ConnectionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConnectionsController {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionsController.class);

    private final ConnectionsService service;

    public ConnectionsController(@Autowired ConnectionsService service) {
        this.service = service;
    }

    /**
     * Lists all the connections found registered.
     * @return all city connections
     */
    @GetMapping("/connections")
    public ResponseEntity<Iterable<CityConnection>> listConnections() {
        logger.info("Listing all connections");
        final Iterable<CityConnection> connections = service.listConnections();

        if (logger.isDebugEnabled()) {
            logger.debug("Connections: %s", connections);
        }
        return ResponseEntity.ok(connections);
    }

    /**
     * Saves a connection into the internal registry.
     *
     * @param connection The connection to be inserted (if id == null) or to be saved (if id != null)
     * @return The persisted connection with id filled
     */
    @PutMapping("/connections")
    public ResponseEntity<CityConnection> saveConnection(@RequestBody CityConnection connection) {
        logger.debug("Saving connection %s", connection);
        return ResponseEntity.ok(service.saveConnection(connection));
    }

    /**
     * Deletes a connection from the registry.
     *
     * If the connection is not found in the registry, it will
     * return a no content instead.
     *
     * @param connection A connection to be deleted
     * @return The deleted connection. If no connection has been found, the request will return a no content
     */
    @DeleteMapping("/connections")
    public ResponseEntity<CityConnection> deleteConnection(@RequestBody CityConnection connection) {
        logger.info("Deleting city [%s]", connection);

        if (!service.findConnection(connection.getId()).isPresent()) {
            logger.info("Connection [%s] not found. Returning no content", connection);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(service.deleteConnection(connection));
    }



}
