package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotBlank
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres!")
    private String name;

    @NotBlank
    @Size(max = 11, min = 11, message = "CPF deve ter 11 números!")
    private String cpf;
}
