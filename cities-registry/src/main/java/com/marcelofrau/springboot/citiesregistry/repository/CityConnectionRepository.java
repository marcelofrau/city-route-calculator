package com.marcelofrau.springboot.citiesregistry.repository;

import com.marcelofrau.springboot.citiesregistry.model.CityConnection;
import org.springframework.data.repository.CrudRepository;

/**
 * CityConnectionRepository represents the repository that will persist and use
 * all the data related with CityConnection.
 *
 * This interface uses the SpringData framework so, new queries can be created
 * here if needed.
 */
public interface CityConnectionRepository extends CrudRepository<CityConnection, Long> {

}
