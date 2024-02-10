package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long gameId;

    @NotNull
    @Min(value = 1, message = "Dias alugados devem ser maiores que 0")
    private int daysRented;
}
