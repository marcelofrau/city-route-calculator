package com.marcelofrau.springboot.citiesregistry.controller;

import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.service.CitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

/**
 * CitiesController represents a controller that can save, list and
 * delete cities that are registered in this micro-service.
 */
@RestController
public class CitiesController {

    private static final Logger logger = LoggerFactory.getLogger(CitiesController.class);

    private final CitiesService service;

    public CitiesController(@Autowired CitiesService citiesService) {
        logger.trace("Initializing CitiesController.");
        this.service = citiesService;
    }

    /**
     * List all registered cities found registered
     * in this micro-service.
     * @return List of cities
     */
    @GetMapping("/cities")
    @ResponseBody
    public ResponseEntity<Iterable<City>> listCities() {
        final Iterable<City> cities = service.listCities();

        if (logger.isDebugEnabled()) {
            logger.debug("List of cities acquired, returning: [%s]", cities);
        }

        logger.info("Listing registered cities.");
        return ResponseEntity.ok(cities);
    }

    /**
     * Inserts a new city into the registry of this
     * micro-service
     * @param city A city with empty ID will be inserted and with ID filled will be saved.
     * @return The new inserted city with the new Id if it was null.
     */
    @PutMapping("/cities")
    @ResponseBody
    public ResponseEntity<City> saveCity(@RequestBody City city) {
        logger.info("Saving city [%s]", city);
        return ResponseEntity.ok(service.saveCity(city));
    }

    /**
     * Deletes a city from the registry of this
     * micro-service.
     * @param city A city to be removed
     * @return The removed city
     */
    @DeleteMapping("/cities")
    public ResponseEntity<City> deleteCity(@RequestBody City city) {
        logger.info("Deleting city [%s]", city);

        if (!service.findCity(city.getId()).isPresent()) {
            logger.info("City [%s] not found. Returning no content", city);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(service.deleteCity(city));
    }

    /**
     * Performs a search to find a city, this can be by Id or by
     * name.
     *
     * If both id and name is informed, the request will return
     * a bad request.
     *
     * @param id Optional argument, if indicated, the search will be by id
     * @param name Optional argument, if indicated the search will be by name
     * @return
     */
    @GetMapping("/cities/find")
    public ResponseEntity<Iterable<City>> findCity(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        if (id != null && name != null) {
            return ResponseEntity.badRequest().build();
        }
        if (id != null) {
            logger.debug("Finding city by id '%s'", id);
            return findById(id);
        }
        if (name != null) {
            logger.debug("Finding city by name '%s'", name);
            return findByName(name);
        }

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Iterable<City>> findByName(String name) {
        final Iterable<City> cities = service.findCity(name);

        return ResponseEntity.ok(cities);
    }

    private ResponseEntity<Iterable<City>> findById(@RequestParam(required = false) Long id) {
        final Optional<City> city = service.findCity(id);
        if (!city.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(Collections.singletonList(city.get()));
    }


}
