package com.london.reboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(SpringExtension.class)
class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Test
    void retry() {
        assertDoesNotThrow(() -> sampleService.retry());
    }

    @Configuration
    @EnableRetry
    static class Config {
        @Bean
        SampleService sampleService()
        {
            return new SampleService();
        }
    }

}