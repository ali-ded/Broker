package com.demo.broker.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class User {
    private BigDecimal amount;
    private final List<Order> orders;

    public User(BigDecimal amount) {
        this.amount = amount;
        orders = new ArrayList<>();
    }

    public void add(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void subtract(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("amount=" + amount)
                .add("orders=" + orders)
                .toString();
    }
}
