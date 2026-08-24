package com.sunrisedental.filter;

import org.junit.jupiter.api.BeforeEach;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;

class AuthenticationFilterTest {

    private AuthenticationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {

        filter =
                new AuthenticationFilter();

        request =
                mock(HttpServletRequest.class);

        response =
                mock(HttpServletResponse.class);

        chain =
                mock(FilterChain.class);
    }
}