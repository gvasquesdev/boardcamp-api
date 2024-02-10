package com.boardcamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.ExceptionConflict;
import com.boardcamp.api.exceptions.ExceptionNotFound;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    final CustomerRepository customersRepository;

    CustomerService(CustomerRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<CustomerModel> findAll() {
        return customersRepository.findAll();
    }

    public CustomerModel findById(Long id) {
        return customersRepository.findById(id).orElseThrow(() -> new ExceptionNotFound("This user does not exist!"));
    }

    public CustomerModel save(CustomerDTO dto) {

        if (customersRepository.existsByCpf(dto.getCpf())) {
            throw new ExceptionConflict("This CPF is already registered!");
        }

        CustomerModel customer = new CustomerModel(dto);
        return customersRepository.save(customer);
    }
}
