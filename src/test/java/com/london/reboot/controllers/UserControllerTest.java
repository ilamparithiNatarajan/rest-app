package com.london.reboot.controllers;

import com.london.reboot.TestRedisConfiguration;
import com.london.reboot.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    private static final String HTTP_URL = "http://localhost:%d%s";
    @LocalServerPort
    private int port;
    final private TestRestTemplate restTemplate = new TestRestTemplate();

    @ParameterizedTest
    @MethodSource("names")
    void addUsers(String firstName, String lastName) {
        var requestBody = String.format("{\"firstName\":\"%s\", \"lastName\":\"%s\"}", firstName, lastName);
        var headers = new HttpHeaders();
        headers.add("Content-Type", APPLICATION_JSON_VALUE);
        headers.add("Accept", ALL_VALUE);
        final var exchange =
                restTemplate.exchange(
                        HTTP_URL.format(HTTP_URL, port, "/users/new"),
                        HttpMethod.POST,
                        new HttpEntity<>(requestBody, headers),
                        String.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(String.format("user created : %s %s", firstName, lastName), exchange.getBody());
    }

    @Test
    void getUsers() {
        final var exchange =
                restTemplate.exchange(
                        HTTP_URL.format(HTTP_URL, port, "/users"),
                        HttpMethod.GET,
                        new HttpEntity<>(new HttpHeaders()),
                        User[].class);
        assertEquals(5, Objects.requireNonNull(exchange.getBody()).length);

    }

    private static Stream<Arguments> names() {
        return Stream.of(
                Arguments.of("alpha", "bravo"),
                Arguments.of("charlie", "delta"),
                Arguments.of("echo", "foxtrot"),
                Arguments.of("golf", "hotel"),
                Arguments.of("india", "juliet")
        );
    }

}
