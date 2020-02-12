package com.london.reboot.controllers;


import com.london.reboot.database.MySQLAccess;
import com.london.reboot.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authenticate")
public class AuthenticateResource {

    @Autowired
    private MySQLAccess mySQLAccess;

    @PostMapping("/{custId}")
    public String authenticate(@PathVariable("custId") final String customerId, @RequestBody final Customer customer) throws Exception {
        System.out.println("customer id received: " + customerId);
        System.out.println("customer: " + customer.toString());
        mySQLAccess.readDataBase();
        return "submitted";
    }
}