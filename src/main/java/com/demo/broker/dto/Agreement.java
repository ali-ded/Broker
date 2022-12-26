package com.demo.broker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Agreement(String userName, String operation, String instrument, int quantity, BigDecimal price,
                        LocalDateTime dateTimeAgreement) {
}
