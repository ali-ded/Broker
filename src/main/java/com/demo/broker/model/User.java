package com.demo.broker.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    private BigDecimal amount;
    private final List<Order> orders;

    public User(BigDecimal amount) {
        this.amount = amount;
        orders = new CopyOnWriteArrayList<>();
    }

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
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
