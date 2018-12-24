package com.marcelofrau.springboot.routecalculator.controller;

import com.marcelofrau.springboot.routecalculator.model.RouteResponse;
import com.marcelofrau.springboot.routecalculator.service.RouteCalculatorService;
import com.marcelofrau.springboot.routecalculator.service.utils.CityNotFoundException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * RouteCalculatorController is responsible to provide a way
 * to calculate paths between cities and provide a optimized path
 * to go from a city to another.
 */
@Controller
@Api(value="Route Calculator Controller", description = "RouteCalculatorController is responsible to provide a way" +
        " to calculate paths between cities and provide a optimized path" +
        " to go from a city to another.")
public class RouteCalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(RouteCalculatorController.class);

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
    @ApiOperation(value="his method calculates the route from a city to another. \n" +
            "The response will contain two main information:\n" +
            "   *   1- The minimum time to go from one to other city.\n" +
            "   *   2- The minimum path to go from one to other city.", response = Iterable.class)
    @ApiResponses(value={
            @ApiResponse(code = 400, message = "Bad content if any of fromCityName or toCityName has been found"),
            @ApiResponse(code = 200, message = "Successfully retrieved connections"),
            @ApiResponse(code = 404, message = "Not found if cities is unreachable by any connection"),
    })
    public ResponseEntity<RouteResponse> calculateRoute(@RequestParam String fromCityName, @RequestParam String toCityName) {
        logger.info("Route Calculation requested from {} to {}", fromCityName, toCityName);
        try {
            final Optional<RouteResponse> routeResponse = service.calculateRoute(fromCityName, toCityName);
            if (routeResponse.isPresent()) {
                return ResponseEntity.ok(routeResponse.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (CityNotFoundException e) {
            logger.error("No city has been found, please ensure your city name is defined properly.", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
