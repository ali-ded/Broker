package com.demo.broker.dto;

import com.demo.broker.model.Order;
import com.demo.broker.validator.ValueOfEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderNewDto(@NotNull @ValueOfEnum(enumClass = Order.Operation.class) String operation,
                          @NotNull @ValueOfEnum(enumClass = Order.Instrument.class) String instrument,
                          @Positive int quantity,
                          @Digits(integer = 12, fraction = 2) @Positive @NotNull BigDecimal price,
                          @Future LocalDateTime activeUntil) {
    public OrderNewDto(@NotNull @ValueOfEnum(enumClass = Order.Operation.class) String operation,
                       @NotNull @ValueOfEnum(enumClass = Order.Instrument.class) String instrument,
                       @Positive int quantity,
                       @Digits(integer = 12, fraction = 2) @Positive @NotNull BigDecimal price,
                       @Future LocalDateTime activeUntil) {
        this.operation = operation.toUpperCase();
        this.instrument = instrument.toUpperCase();
        this.quantity = quantity;
        this.price = price;
        this.activeUntil = activeUntil;
    }
}
