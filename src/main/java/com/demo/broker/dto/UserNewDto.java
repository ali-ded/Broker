package com.demo.broker.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UserNewDto(@NotBlank String userName,
                         @Digits(integer = 12, fraction = 2) @Positive @NotNull BigDecimal amount) {
}
