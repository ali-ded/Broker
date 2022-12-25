package com.demo.broker.dto;

import com.demo.broker.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private String operation;
    private String instrument;
    private int quantity;
    private BigDecimal price;
    private boolean isActive;
    private List<Order> agreements;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public void setAgreements(List<Order> agreements) {
        this.agreements = agreements;
    }
}
