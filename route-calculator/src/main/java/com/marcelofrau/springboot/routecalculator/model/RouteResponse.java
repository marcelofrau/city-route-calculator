package com.marcelofrau.springboot.routecalculator.model;

import java.util.List;
import java.util.Objects;

/**
 * RouteResponse is used to be the response containing
 * the origin city, destination city, the minimum connections
 * to travel from a city to another, the minimum time from a city
 * to another considering any other path that contains a smaller time,
 * the min connections path and the min time connections path.
 */
public class RouteResponse {

    private City originCity;
    private City destinationCity;

    private Integer minConnectionsToTravel;
    private Integer minTimeToTravelInHours;

    private List<String> minConnectionsPath;
    private List<String> minConnectionsInTime;

    public RouteResponse() {}

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

    public Integer getMinConnectionsToTravel() {
        return minConnectionsToTravel;
    }

    public void setMinConnectionsToTravel(Integer minConnectionsToTravel) {
        this.minConnectionsToTravel = minConnectionsToTravel;
    }

    public Integer getMinTimeToTravelInHours() {
        return minTimeToTravelInHours;
    }

    public void setMinTimeToTravelInHours(Integer minTimeToTravelInHours) {
        this.minTimeToTravelInHours = minTimeToTravelInHours;
    }

    public List<String> getMinConnectionsPath() {
        return minConnectionsPath;
    }

    public void setMinConnectionsPath(List<String> minConnectionsPath) {
        this.minConnectionsPath = minConnectionsPath;
    }

    public List<String> getMinConnectionsInTime() {
        return minConnectionsInTime;
    }

    public void setMinConnectionsInTime(List<String> minConnectionsInTime) {
        this.minConnectionsInTime = minConnectionsInTime;
    }

    @Override
    public String toString() {
        return "RouteResponse{" +
                "originCity=" + originCity +
                ", destinationCity=" + destinationCity +
                ", minConnectionsToTravel=" + minConnectionsToTravel +
                ", minTimeToTravelInHours=" + minTimeToTravelInHours +
                ", minConnectionsPath=" + minConnectionsPath +
                ", minConnectionsInTime=" + minConnectionsInTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteResponse that = (RouteResponse) o;
        return Objects.equals(originCity, that.originCity) &&
                Objects.equals(destinationCity, that.destinationCity) &&
                Objects.equals(minConnectionsToTravel, that.minConnectionsToTravel) &&
                Objects.equals(minTimeToTravelInHours, that.minTimeToTravelInHours) &&
                Objects.equals(minConnectionsPath, that.minConnectionsPath) &&
                Objects.equals(minConnectionsInTime, that.minConnectionsInTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originCity, destinationCity, minConnectionsToTravel, minTimeToTravelInHours, minConnectionsPath, minConnectionsInTime);
    }
}
