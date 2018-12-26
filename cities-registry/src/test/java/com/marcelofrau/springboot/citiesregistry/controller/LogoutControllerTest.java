package com.marcelofrau.springboot.citiesregistry.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LogoutControllerTest {

    @InjectMocks
    private LogoutController controller;

    @Test
    public void shouldLogout() {
        final HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
        final HttpServletResponse mockedResponse = mock(HttpServletResponse.class);

        final String response = controller.logoutPage(mockedRequest, mockedResponse);

        assertNotNull(response);
        assertEquals("redirect:/cities", response);
    }
}
