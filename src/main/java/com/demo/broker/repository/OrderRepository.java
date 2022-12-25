package com.demo.broker.repository;

import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.model.Order;
import com.demo.broker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private final List<Order> currentSessionOrders;
    private final UserRepository userRepository;

    @Autowired
    public OrderRepository(UserRepository userRepository) {
        currentSessionOrders = new ArrayList<>();
        this.userRepository = userRepository;
    }

    public void add(Order order) throws UserNotFoundException {
        currentSessionOrders.add(order);
        User user = userRepository.get(order.getUserName()).orElseThrow(
                () -> new UserNotFoundException(String.format("Error adding new order to user %s", order.getUserName())));
        user.getOrders().add(order);
    }

    public List<Order> getCurrentSessionOrders() {
        return currentSessionOrders;
    }

    public List<Order> getOrdersByUserName(String userName) throws UserNotFoundException {
        return userRepository.get(userName).orElseThrow(
                () -> new UserNotFoundException(String.format("User '%s' not found", userName)))
                .getOrders();
    }

    public void clear() {
        currentSessionOrders.clear();
    }
}
