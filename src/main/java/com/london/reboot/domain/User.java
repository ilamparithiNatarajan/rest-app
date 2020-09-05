package com.london.reboot.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RedisHash("User")
public @Data class User {

    private @Id
    Long id;

    @NotBlank(message = "firstName is mandatory")
    @Size(min = 1, max = 25, message
            = "About Me must be between 1 and 25 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "must not contain number or special characters")
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    @Size(min = 1, max = 25, message
            = "About Me must be between 1 and 25 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "must not contain number or special characters")
    private String lastName;
}
