package com.marcelofrau.springboot.citiesregistry.service;

import com.marcelofrau.springboot.citiesregistry.model.City;
import com.marcelofrau.springboot.citiesregistry.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the main service for all city finding/registering
 * information.
 *
 * In this class you can create, remove, list and even find
 * all cities registered in this micro-service.
 *
 * @see City
 */
@Service
public class CitiesService {

    private final CityRepository repository;

    public CitiesService(@Autowired CityRepository repository) {
        this.repository = repository;
    }

    /**
     * List all cities registered
     * @return Iterable of all cities
     */
    public Iterable<City> listCities() {
        return repository.findAll();
    }

    /**
     * Delete a city from the registry.
     * @param city City to be removed
     * @return The removed city
     */
    public City deleteCity(City city) {
        repository.delete(city);
        return city;
    }

    /**
     * Saves a city on the registry.
     *
     * If the city is with null id, it will be created
     * and if the city already contain an Id, it will
     * be updated.
     *
     * @param city City to be inserted/saved
     * @return The city containing the id filled if it was just created.
     */
    public City saveCity(City city) {
        return repository.save(city);
    }

    /**
     * Finds a city by an Id
     * @param id Id of a city.
     * @return A city with the given Id, if no city is found, it will return null.
     */
    public Optional<City> findCity(Long id) {
        return repository.findById(id);
    }

    /**
     * Find a city from a given name.
     * @param name Name of a city
     * @return A iterable of cities that match the same name. If no city is found, the iterable will be
     *          empty
     */
    public Iterable<City> findCity(String name) {
        return repository.findByName(name);
    }

}
