package com.marcelofrau.springboot.routecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Bootstrap class to start the main RouteCalculatorApplication
 *
 * This application is responsible to provide a way to calculate
 * a shortest path in time or in connections between cities.
 *
 * You need to have the CitiesRegistryApplication running to use
 * this micro-service.
 */
@SpringBootApplication
//@EnableCircuitBreaker
public class RouteCalculatorApplication {

	/**
	 * Initial main method, this starts the main application.
	 * @param args Arguments, see spring-boot arguments to know more about it.
	 */
	public static void main(String[] args) {
		SpringApplication.run(RouteCalculatorApplication.class, args);
	}
}
