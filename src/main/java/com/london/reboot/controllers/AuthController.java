package com.london.reboot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(produces = "text/plain")
    @ResponseBody
    String get() {
        return "works";
    }

}
