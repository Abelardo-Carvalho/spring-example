package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductModelDto(@NotBlank String name, @NotNull BigDecimal value) {
}
