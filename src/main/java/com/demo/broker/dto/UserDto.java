package com.demo.broker.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserDto {
    private BigDecimal amount;
    private List<OrderDto> orders;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
