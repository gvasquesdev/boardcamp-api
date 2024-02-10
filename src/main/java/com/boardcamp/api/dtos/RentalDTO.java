package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalDTO {

    @NotNull
    @Positive(message = "CustomerId should be greater than 0!")
    private Long customerId;

    @NotNull
    @Positive(message = "CustomerId should be greater than 0!")
    private Long gameId;

    @NotNull
    @Min(value = 1, message = "Days rented should be greater than 0!")
    private int daysRented;
}
