package com.marcelofrau.springboot.routecalculator.controller;

import com.marcelofrau.springboot.routecalculator.model.RouteResponse;
import com.marcelofrau.springboot.routecalculator.service.RouteCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * RouteCalculatorController is responsible to provide a way
 * to calculate paths between cities and provide a optimized path
 * to go from a city to another.
 */
@Controller
public class RouteCalculatorController {

    private final RouteCalculatorService service;

    public RouteCalculatorController(@Autowired RouteCalculatorService service) {
        this.service = service;
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
    @GetMapping("/calculate-route")
    @ResponseBody
    public RouteResponse calculateRoute(@RequestParam String fromCityName, @RequestParam String toCityName) {
        return service.calculateRoute(fromCityName, toCityName);
    }

}
