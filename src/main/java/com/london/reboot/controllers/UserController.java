package com.london.reboot.controllers;

import com.london.reboot.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping
    public String createUser(@RequestBody User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        return "user created : " + fullName;
    }
}
