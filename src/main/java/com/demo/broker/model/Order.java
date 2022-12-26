package com.demo.broker.model;

import com.demo.broker.dto.Agreement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Order {
    private String userName;
    private final Operation operation;
    private final Instrument instrument;
    private final int quantity;
    private int quantityRemainder;
    private final BigDecimal price;
    private final LocalDateTime activeUntil;
    private boolean isActive;
    private final List<Agreement> agreements;

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
        agreements = new CopyOnWriteArrayList<>();
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

    public List<Agreement> getAgreements() {
        return agreements;
    }

    public void addAgreement(Agreement agreement) {
        agreements.add(agreement);
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
