package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GameDTO {
    @NotBlank
    @Size(max = 150, message = "No máximo 150 caracteres!")
    private String name;

    @NotBlank
    @Size(max = 500, message = "O tamanho máximo do link da imagem deve ser de 500 caracteres!")
    private String image;

    @NotNull
    @Min(1)
    private int stockTotal;

    @NotNull
    @Min(1)
    private double pricePerDay;
}
