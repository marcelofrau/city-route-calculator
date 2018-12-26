package com.marcelofrau.springboot.routecalculator.controller;

import com.marcelofrau.springboot.routecalculator.model.response.RouteResponse;
import com.marcelofrau.springboot.routecalculator.service.RouteCalculatorService;
import com.marcelofrau.springboot.routecalculator.service.utils.CityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RouteCalculatorControllerTest {

    @InjectMocks
    private RouteCalculatorController controller;

    @Mock
    private RouteCalculatorService service;

    @Test
    public void shouldReturnRouteResponse() {
        final RouteResponse routeResponse = new RouteResponse();
        when(service.calculateRoute(any(), any())).thenReturn(Optional.of(routeResponse));

        final ResponseEntity<RouteResponse> response = controller.calculateRoute("foo", "bar");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(routeResponse, response.getBody());

        verify(service, times(1)).calculateRoute(any(), any());
    }

    @Test
    public void shouldNotReturnRouteResponse() {
        when(service.calculateRoute(any(), any())).thenReturn(Optional.empty());

        final ResponseEntity<RouteResponse> response = controller.calculateRoute("foo", "bar");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(service, times(1)).calculateRoute(any(), any());
    }

    @Test
    public void shouldNotReturnRouteResponseWhenCityNotFound() {
        when(service.calculateRoute(any(), any())).thenThrow(new CityNotFoundException("City not found test"));

        final ResponseEntity<RouteResponse> response = controller.calculateRoute("foo", "bar");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(service, times(1)).calculateRoute(any(), any());
    }


}
