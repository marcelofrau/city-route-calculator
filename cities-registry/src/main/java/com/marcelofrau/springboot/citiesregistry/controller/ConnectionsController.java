package com.marcelofrau.springboot.citiesregistry.controller;

import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import com.marcelofrau.springboot.citiesregistry.service.ConnectionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ConnectionsController represents a controller that can save, list and
 * delete city connections that are registered in this micro-service.
 *
 * @see CityConnection
 * @see com.marcelofrau.springboot.citiesregistry.model.City
 */
@RestController
@Api(value="Connections Controller", description = "ConnectionsController represents a controller that can save, list and" +
        " delete city connections that are registered in this micro-service.")
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
    @ResponseBody
    @ApiOperation(value="Lists all the connections found registered.", response = Iterable.class)
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
    })
    public ResponseEntity<Iterable<CityConnection>> listConnections() {
        logger.info("Listing all connections");
        final Iterable<CityConnection> connections = service.listConnections();

        if (logger.isDebugEnabled()) {
            logger.debug("Connections: {}", connections);
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
    @ResponseBody
    @ApiOperation(value="Saves a connection into the internal registry.", response = CityConnection.class)
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Successfully saved city connection"),
    })
    public ResponseEntity<CityConnection> saveConnection(@RequestBody CityConnection connection) {
        logger.debug("Saving connection {}", connection);
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
    @ResponseBody
    @ApiOperation(value="Deletes a connection from the registry.", response = CityConnection.class)
    @ApiResponses(value={
            @ApiResponse(code = 404, message = "No content in case of no found connection"),
            @ApiResponse(code = 200, message = "Successfully deleted connection"),
    })
    public ResponseEntity<CityConnection> deleteConnection(@RequestBody CityConnection connection) {
        logger.info("Deleting city [{}]", connection);

        if (!service.findConnection(connection.getId()).isPresent()) {
            logger.info("Connection [{}] not found. Returning no content", connection);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(service.deleteConnection(connection));
    }



}
