package com.demo.broker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

public class Order {
    private User user;
    private Operation operation;
    private Instrument instrument;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime activeUntil;
    private boolean isActive;
    private List<Order> agreements;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public Operation getOperation() {
        return operation;
    }

    public Order setOperation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Order setInstrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Order setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Order setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getActiveUntil() {
        return activeUntil;
    }

    public Order setActiveUntil(LocalDateTime activeUntil) {
        this.activeUntil = activeUntil;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public Order setActive(boolean active) {
        isActive = active;
        return this;
    }

    public List<Order> getAgreements() {
        return agreements;
    }

    public Order setAgreements(List<Order> agreements) {
        this.agreements = agreements;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("operation=" + operation)
                .add("instrument=" + instrument)
                .add("quantity=" + quantity)
                .add("price=" + price)
                .add("activeUntil=" + activeUntil)
                .add("isActive=" + isActive)
                .add("agreements=" + agreements)
                .toString();
    }

    public enum Operation {
        BUY,
        SELL
    }

    public enum Instrument {
        PETROLEUM,
        SECURITIES,
        CURRENCY,
        AGRICULTURAL
    }
}
