package com.london.reboot;

import com.sun.management.HotSpotDiagnosticMXBean;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestClass;

import javax.management.MBeanServer;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestfulApplicationTests {


	private static final String HTTP_URL = "http://localhost:%d%s";
	@LocalServerPort
	private int port;
	final private TestRestTemplate restTemplate = new TestRestTemplate();

  @Test
  void testHelloController() {
    final var exchange =
        restTemplate.exchange(
            HTTP_URL.format(HTTP_URL, port, "/test"),
            HttpMethod.GET,
            new HttpEntity<>(new HttpHeaders()),
            String.class);
    assertEquals(HttpStatus.OK, exchange.getStatusCode());
    assertTrue(Objects.requireNonNull(exchange.getBody()).contains("hello ilam"));
    System.out.println("test success !");
		}

  @Test
  void testSwagger() {
      final var ui =
              restTemplate.exchange(
                      HTTP_URL.format(HTTP_URL, port, "/swagger-ui/index.html#"),
                      HttpMethod.GET,
                      new HttpEntity<>(new HttpHeaders()),
                      String.class);
      assertEquals(HttpStatus.OK, ui.getStatusCode());
      final var api =
              restTemplate.exchange(
                      HTTP_URL.format(HTTP_URL, port, "/v3/api-docs"),
                      HttpMethod.GET,
                      new HttpEntity<>(new HttpHeaders()),
                      String.class);
      assertEquals(HttpStatus.OK, api.getStatusCode());

  }
}
