package com.demo.broker.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class User {
    private PersonalAccount account;
    List<Order> orders;

    public User(PersonalAccount account) {
        this.account = account;
        orders = new ArrayList<>();
    }

    public PersonalAccount getAccount() {
        return account;
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
                .add("account=" + account)
                .add("orders=" + orders)
                .toString();
    }

    public static class PersonalAccount {
        private BigDecimal amount;

        public PersonalAccount(BigDecimal amount) {
            this.amount = amount;
        }

        public void add(BigDecimal number) {
            amount = amount.add(number);
        }

        public void subtract(BigDecimal number) {
            amount = amount.subtract(number);
        }

        public BigDecimal getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", PersonalAccount.class.getSimpleName() + "[", "]")
                    .add("amount=" + amount)
                    .toString();
        }
    }
}
