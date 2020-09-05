package com.london.reboot.controllers;

import com.london.reboot.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    @PostMapping(path = "/new", consumes = "application/json", produces = "text/plain")
    public @ResponseBody
    String createUser(@Valid @RequestBody User user) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        return "user created : " + fullName;
    }
}
