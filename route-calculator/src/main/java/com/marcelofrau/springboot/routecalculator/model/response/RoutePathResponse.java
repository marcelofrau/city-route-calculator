package com.marcelofrau.springboot.routecalculator.model.response;

import java.util.List;
import java.util.Objects;

/**
 * Represents a RoutePathResponse info.
 * This is intended to have information as the counter of the path
 * and a List of connections.
 *
 * The path could be in relation to time or in relation to connections
 * It can also be used to represent a path using any other mechanism to
 * calculate the path and this class has the responsibility to represent
 * this data.
 */
public final class RoutePathResponse {
    private String description;
    private Integer count;
    private List<String> pathOfConnections;

    public RoutePathResponse(String description, Integer count, List<String> pathOfConnections) {
        this.description = description;
        this.count = count;
        this.pathOfConnections = pathOfConnections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getPathOfConnections() {
        return pathOfConnections;
    }

    public void setPathOfConnections(List<String> pathOfConnections) {
        this.pathOfConnections = pathOfConnections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutePathResponse that = (RoutePathResponse) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(count, that.count) &&
                Objects.equals(pathOfConnections, that.pathOfConnections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, count, pathOfConnections);
    }

    @Override
    public String toString() {
        return "RoutePathResponse{" +
                "description='" + description + '\'' +
                ", count=" + count +
                ", pathOfConnections=" + pathOfConnections +
                '}';
    }
}
