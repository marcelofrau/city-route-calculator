package com.marcelofrau.springboot.routecalculator.model.dijsktra;

import com.marcelofrau.springboot.routecalculator.model.City;

import java.util.Objects;

public class Vertex {

    private final Long id;
    private final City city;

    public Vertex(Long id, City city) {
        this.id = id;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id) &&
                Objects.equals(city, vertex.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}