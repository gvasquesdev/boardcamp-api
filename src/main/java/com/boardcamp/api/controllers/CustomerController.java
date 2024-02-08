package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.services.CustomerService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/customers")
public class CustomerController {
    final CustomerService customersService;

    public CustomerController(CustomerService customersService) {
        this.customersService = customersService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomersById(@PathVariable Long id) {
        CustomerModel customer = customersService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerModel> createNewCustomers(@RequestBody @Valid CustomerDTO body) {
        CustomerModel customer = customersService.save(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }
}
