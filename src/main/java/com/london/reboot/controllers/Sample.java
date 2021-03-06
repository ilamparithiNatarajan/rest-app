package com.london.reboot.controllers;


import com.london.reboot.service.SampleService;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("test")
public class Sample {

    final static Logger logger = LoggerFactory.getLogger(Sample.class);

    @Value("${mockurl}")
    private String url;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SampleService sampleService;

    @GetMapping
    public String getVersion() {
        return "hello ilam";
    }

    @PostMapping(path = "log/{status}")
    public ResponseEntity<String> log(@PathVariable int status) {
        var expectedStatus = HttpStatus.resolve(status);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("custom", "xxxxx");
        var request = "{\"body\":\"orange\"}";

        var httpEntity = new HttpEntity<String>(request, headers);
        logger.debug("calling {}", url);
        var finalUrl = url + "/" + Objects.requireNonNull(expectedStatus).value();
        logger.debug("final url {}", finalUrl);
        return restTemplate.exchange(finalUrl, HttpMethod.POST, httpEntity,  String.class);

    }

    @GetMapping(path = "retry")
    public ResponseEntity<String> retry() {
        sampleService.retry();
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }




}
