package com.boardcamp.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.services.RentalService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rentals")
public class RentalController {

    final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping()
    public ResponseEntity<List<RentalModel>> getRentals() {
        List<RentalModel> rentals = rentalService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }

    @PostMapping()
    public ResponseEntity<RentalModel> createRental(@RequestBody @Valid RentalDTO body) {
        RentalModel rent = rentalService.save(body);
        return ResponseEntity.status(HttpStatus.OK).body(rent);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<RentalModel> returnRental(@PathVariable Long id, @RequestBody String entity) {
        RentalModel rental = rentalService.update(id);
        return ResponseEntity.status(HttpStatus.OK).body(rental);
    }

}
