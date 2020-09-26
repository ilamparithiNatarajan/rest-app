package com.london.reboot.controllers;

import com.london.reboot.data.UserRepository;
import com.london.reboot.domain.User;
import com.london.reboot.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(consumes = "application/json", produces = "text/plain")
    public @ResponseBody
    String createUser(@Valid @RequestBody User user) {
        logger.info("user details in request : fn:{}, ln:{}", user.getFirstName(), user.getLastName());
        var fullName = user.getFirstName() + " " + user.getLastName();
        var savedUser = userRepository.save(user);
        logger.debug("id of saved user : {}", savedUser.getId());
        return "user created : " + fullName;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public @ResponseBody
    User getUser(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
