package com.demo.broker.repository;

import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.model.Order;
import com.demo.broker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class OrderRepository {
    private final List<Order> currentSessionOrders;
    private final UserRepository userRepository;
    private boolean isSessionActive = false;

    @Autowired
    public OrderRepository(UserRepository userRepository) {
        currentSessionOrders = new CopyOnWriteArrayList<>();
        this.userRepository = userRepository;
    }

    public void add(Order order) throws UserNotFoundException {
        User user = userRepository.get(order.getUserName()).orElseThrow(
                () -> new UserNotFoundException(String.format("Error adding new order to user %s", order.getUserName())));
        user.getOrders().add(order);
        currentSessionOrders.add(order);
    }

    public List<Order> getCurrentSessionOrders() {
        return currentSessionOrders;
    }

    public void clear() {
        currentSessionOrders.clear();
    }

    public void setActiveSession(boolean isSessionActive) {
        this.isSessionActive = isSessionActive;
    }

    public boolean isSessionActive() {
        return isSessionActive;
    }
}
