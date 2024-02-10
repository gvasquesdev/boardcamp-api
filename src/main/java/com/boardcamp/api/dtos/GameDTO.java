package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameDTO {
    @NotBlank
    @Size(max = 150, message = "Customer name should be 150 characters maximum!")
    private String name;

    @NotBlank
    @Size(max = 500, message = "Image URL should be 500 characters maximum!")
    private String image;

    @NotNull
    @Min(1)
    private int stockTotal;

    @NotNull
    @Min(1)
    private double pricePerDay;
}
