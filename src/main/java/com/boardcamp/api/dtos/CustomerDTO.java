package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {
    @NotBlank
    @Size(max = 150, message = "Customer name should be 150 characters maximum!!")
    private String name;

    @NotBlank
    @Size(max = 11, min = 11, message = "CPF should have exactly 11 numbers!")
    private String cpf;
}
