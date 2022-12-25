package com.demo.broker.service;

import com.demo.broker.dto.OrderDto;
import com.demo.broker.dto.OrderNewDto;
import com.demo.broker.dto.UserDto;
import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.mapper.OrderMapper;
import com.demo.broker.model.Order;
import com.demo.broker.repository.OrderRepository;
import com.demo.broker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public void add(OrderNewDto orderNewDto) throws UserNotFoundException {
        Order order = orderMapper.orderNewDtoToOrder(orderNewDto);
        orderRepository.add(order);
        LOGGER.info("New order added successfully");
    }

    public List<Order> getCurrentSessionOrders() {
        List<Order> orderList = orderRepository.getCurrentSessionOrders();
        LOGGER.info("List of current session orders successfully received");
        return orderList;
    }

    public void cancelCurrentSessionOrders() {
        orderRepository.clear();
        LOGGER.info("All orders for the current session have been cancelled");
    }
}
