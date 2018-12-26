package com.marcelofrau.springboot.citiesregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Bootstrap class to start the main CitiesRegistryApplication
 *
 * This application contains an internal registry of cities and
 * connections between the cities. In this service is possible
 * to list, create, delete cities and connections.
 */
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CitiesRegistryApplication {

	/**
	 * Initial main method, this starts the main application.
	 * @param args Arguments, see spring-boot arguments to know more about it.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CitiesRegistryApplication.class, args);
	}
}
