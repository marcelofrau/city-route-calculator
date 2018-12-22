package com.marcelofrau.springboot.citiesregistry.repository;

import com.marcelofrau.springboot.citiesregistry.model.City;
import org.springframework.data.repository.CrudRepository;

/**
 * CityRepository represents the repository that will persist and use
 * all the data related with City.
 *
 * This interface uses the SpringData framework so, new queries can be created
 * here if needed.
 */
public interface CityRepository extends CrudRepository<City, Long> {
    Iterable<City> findByName(String name);
}
