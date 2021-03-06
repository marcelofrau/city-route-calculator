package com.marcelofrau.springboot.citiesregistry.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * Represents a connection between cities.
 *
 * Each connection have an Id, an origin city, a
 * destination city and how much time we would need to
 * travel from one city to another. This time is registered
 * in hours.
 *
 * Any connection can be used to go and back between
 * cities, so it is considered a bi-directional
 * connection.
 */
@Entity
public class CityConnection {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private City originCity;

    @ManyToOne
    private City destinationCity;

    private Double distanceInHours;

    /** default constructor */
    public CityConnection() {}

    public CityConnection(City originCity, City destinationCity, Double distanceInHours) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.distanceInHours = distanceInHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getOriginCity() {
        return originCity;
    }

    public void setOriginCity(City originCity) {
        this.originCity = originCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Double getDistanceInHours() {
        return distanceInHours;
    }

    public void setDistanceInHours(Double distanceInHours) {
        this.distanceInHours = distanceInHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityConnection that = (CityConnection) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CityConnection{" +
                "id=" + id +
                ", originCity=" + originCity +
                ", destinationCity=" + destinationCity +
                ", distanceInHours=" + distanceInHours +
                '}';
    }
}
