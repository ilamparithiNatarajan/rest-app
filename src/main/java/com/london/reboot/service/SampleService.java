package com.london.reboot.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    @Retryable(value = RuntimeException.class, maxAttempts = 5,
            backoff = @Backoff(delay = 100))
    public void retry() {
        System.out.println(" ::: failed ::: ");
        throw new RuntimeException();
    }

    @Recover
    public void recover(RuntimeException e) {
        System.out.println(" ::: recovered ::: ");
    }
}
