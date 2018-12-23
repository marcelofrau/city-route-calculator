package com.marcelofrau.springboot.routecalculator.service;

import com.marcelofrau.springboot.routecalculator.model.City;
import com.marcelofrau.springboot.routecalculator.model.CityConnection;
import com.marcelofrau.springboot.routecalculator.model.RouteResponse;
import com.marcelofrau.springboot.routecalculator.service.utils.CityNotFoundException;
import com.marcelofrau.springboot.routecalculator.service.utils.DijkstraAlgorithm;
import com.marcelofrau.springboot.routecalculator.model.dijsktra.Edge;
import com.marcelofrau.springboot.routecalculator.model.dijsktra.Graph;
import com.marcelofrau.springboot.routecalculator.model.dijsktra.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * RouteCalculatorService is a service to calculate a route
 * between cities.
 *
 * This service rely on another micro-service data (CityRegistryApplication)
 */
@Service
public final class RouteCalculatorService {

    private String cityRegistryURL;

    public RouteCalculatorService(
            @Value("${city.registry.url}") String cityRegistryURL) {


        this.cityRegistryURL = cityRegistryURL;
    }

    /**
     * This method calculates the route from a city to another.
     *
     * The response will contain two main information:
     *   1- The minimum time to go from one to other city.
     *   2- The minimum path to go from one to other city.
     *
     * The response will contain the path itself and a counter indicating
     * the minimum time and indicating the quantity of cities that need to be
     * traversed to get to the destination city
     *
     * @see RouteResponse
     *
     * @param fromCityName The origin city to be considered
     * @param toCityName The destination city to be travelled.
     * @return a RouteResponse containing all the information on the path calculations
     */
    public Optional<RouteResponse> calculateRoute(final String fromCityName, final String toCityName) {
        final City[] cities = fetchCities();
        final CityConnection[] connections = fetchConnections();

        final City fromCity = findCity(cities, fromCityName);
        final City toCity = findCity(cities, toCityName);

        if (fromCity == null) {
            throw new CityNotFoundException("Unable to locate city: " + fromCityName);
        }

        if (toCity == null) {
            throw new CityNotFoundException("Unable to locate city: " + toCityName);
        }

        final LinkedList<Vertex> pathInTime = findPath(connections, fromCity, toCity, CityConnection::getDistanceInHours);
        final LinkedList<Vertex> pathInConnections = findPath(connections, fromCity, toCity, (CityConnection) -> 1.0);

        if (pathInConnections == null && pathInTime == null) {
            return Optional.empty();
        }

        final Integer minConnections = pathInTime.size();
        final Integer minTime = pathInConnections.size();

        final List<String> connectionsPath = pathInConnections.stream().map((Vertex v) ->
                v.getCity().getName()).collect(Collectors.toList());

        final List<String> minConnectionsInTime = pathInTime.stream().map((Vertex v) ->
                v.getCity().getName()).collect(Collectors.toList());

        return Optional.of(new RouteResponse(fromCity, toCity, minConnections, minTime, connectionsPath, minConnectionsInTime));
    }

    private CityConnection[] fetchConnections() {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = String.format("%s/%s", cityRegistryURL, "connections");
        final ResponseEntity<CityConnection[]> response = restTemplate.getForEntity(url, CityConnection[].class);

        return response.getBody();
    }

    private City[] fetchCities() {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = String.format("%s/%s", cityRegistryURL, "cities");
        final ResponseEntity<City[]> response = restTemplate.getForEntity(url, City[].class);

        return response.getBody();
    }

    private City findCity(City[] cities, String cityName) {
        for (final City city : cities) {
            if (cityName.equals(city.getName())) {
                return city;
            }
        }

        return null;
    }


    private LinkedList<Vertex> findPath(CityConnection[] connections, City fromCity, City toCity, Function<CityConnection, Double> getDistanceInHours) {
        final Graph graph = buildGraph(connections, getDistanceInHours);
        final DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);

        dijkstraAlgorithm.initialize(new Vertex(fromCity.getId(), fromCity));
        return dijkstraAlgorithm.getShortestPath(new Vertex(toCity.getId(), toCity));
    }

    private Graph buildGraph(CityConnection[] connections, Function<CityConnection, Double> weightCalculator) {

        final HashSet<Vertex> vertices = new HashSet<>();
        final ArrayList<Edge> edges = new ArrayList<>();

        for (final CityConnection connection : connections) {
            final City originCity = connection.getOriginCity();
            final City destinationCity = connection.getDestinationCity();

            final Vertex source = new Vertex(originCity.getId(), originCity);
            final Vertex destination = new Vertex(destinationCity.getId(), destinationCity);

            final Edge edge = new Edge(connection.getId(), source, destination, weightCalculator.apply(connection));

            vertices.add(source);
            vertices.add(destination);
            edges.add(edge);
        }

        return new Graph(new ArrayList<>(vertices), edges);
    }
}
