package com.demo.broker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Order {
    private String userName;
    private final Operation operation;
    private final Instrument instrument;
    private final int quantity;
    private int quantityRemainder;
    private final BigDecimal price;
    private final LocalDateTime activeUntil;
    private boolean isActive;
    private final List<Order> agreements;

    public Order(Operation operation,
                 Instrument instrument,
                 int quantity,
                 BigDecimal price,
                 LocalDateTime activeUntil) {
        this.operation = operation;
        this.instrument = instrument;
        this.quantity = quantity;
        this.quantityRemainder = quantity;
        this.price = price;
        this.activeUntil = activeUntil;
        agreements = new ArrayList<>();
        isActive = true;
    }

    public String getUserName() {
        return userName;
    }

    public Order setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Operation getOperation() {
        return operation;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getQuantityRemainder() {
        return quantityRemainder;
    }

    public void addQuantity(int quantity) {
        this.quantityRemainder += quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantityRemainder -= quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getActiveUntil() {
        return activeUntil;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Order> getAgreements() {
        return agreements;
    }

    public void addAgreement(Order agreement) {
        agreements.add(agreement);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("operation=" + operation)
                .add("instrument=" + instrument)
                .add("quantity=" + quantity)
                .add("quantityRemainder=" + quantityRemainder)
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
