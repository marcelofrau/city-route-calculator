package com.marcelofrau.springboot.routecalculator.model.response;

import com.marcelofrau.springboot.routecalculator.model.City;

import java.util.List;
import java.util.Objects;

/**
 * RouteResponse is used to be the response containing
 * the origin city, destination city, the minimum connections
 * to travel from a city to another, the minimum time from a city
 * to another considering any other path that contains a smaller time,
 * the min connections path and the min time connections path.
 */
public final class RouteResponse {

    private City originCity;
    private City destinationCity;

    private RoutePathResponse minimumPathInTime;
    private RoutePathResponse minimumPathInConnections;

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

    public RoutePathResponse getMinimumPathInTime() {
        return minimumPathInTime;
    }

    public void setMinimumPathInTime(RoutePathResponse minimumPathInTime) {
        this.minimumPathInTime = minimumPathInTime;
    }

    public RoutePathResponse getMinimumPathInConnections() {
        return minimumPathInConnections;
    }

    public void setMinimumPathInConnections(RoutePathResponse minimumPathInConnections) {
        this.minimumPathInConnections = minimumPathInConnections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteResponse that = (RouteResponse) o;
        return Objects.equals(originCity, that.originCity) &&
                Objects.equals(destinationCity, that.destinationCity) &&
                Objects.equals(minimumPathInTime, that.minimumPathInTime) &&
                Objects.equals(minimumPathInConnections, that.minimumPathInConnections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originCity, destinationCity, minimumPathInTime, minimumPathInConnections);
    }

    @Override
    public String toString() {
        return "RouteResponse{" +
                "originCity=" + originCity +
                ", destinationCity=" + destinationCity +
                ", minimumPathInTime=" + minimumPathInTime +
                ", minimumPathInConnections=" + minimumPathInConnections +
                '}';
    }
}
