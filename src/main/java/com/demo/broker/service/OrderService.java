package com.demo.broker.service;

import com.demo.broker.dto.OrderDto;
import com.demo.broker.dto.OrderNewDto;
import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.mapper.OrderMapper;
import com.demo.broker.model.Order;
import com.demo.broker.repository.OrderRepository;
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

    public void add(OrderNewDto orderNewDto, String userName) throws UserNotFoundException {
        Order order = orderMapper.orderNewDtoToOrder(orderNewDto).setUserName(userName);
        orderRepository.add(order);
        LOGGER.info("New order added successfully");
    }

    public List<Order> getCurrentSessionOrders() {
        List<Order> orderList = orderRepository.getCurrentSessionOrders();
        LOGGER.info("List of current session orders successfully received");
        return orderList;
    }

    public List<OrderDto> getCurrentSessionOrdersDto() {
        List<Order> orderList = orderRepository.getCurrentSessionOrders();
        LOGGER.info("List of current session orders successfully received");
        return orderList.stream()
                .map(orderMapper::orderToOrderDto).toList();
    }


    public void setActiveSession(boolean isSessionActive) {
        orderRepository.setActiveSession(isSessionActive);
        if (orderRepository.isSessionActive()) {
            LOGGER.info("The trading session has started");
        } else {
            orderRepository.getCurrentSessionOrders().forEach(order -> order.setActive(false));
            orderRepository.clear();
            LOGGER.info("The trading session has ended");
        }
    }

    public boolean isSessionActive() {
        return orderRepository.isSessionActive();
    }
}
