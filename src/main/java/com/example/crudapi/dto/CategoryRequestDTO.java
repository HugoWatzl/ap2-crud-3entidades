package com.example.crudapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String description;
}
