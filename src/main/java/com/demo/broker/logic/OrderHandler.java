package com.demo.broker.logic;

import com.demo.broker.dto.Agreement;
import com.demo.broker.model.Order;
import com.demo.broker.model.User;
import com.demo.broker.service.OrderService;
import com.demo.broker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderHandler {
    private final OrderService orderService;
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderHandler.class);

    @Autowired
    public OrderHandler(OrderService orderService,
                        UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Scheduled(fixedDelay = 5000)
    private void process() {
        if (orderService.isSessionActive()) {
            List<Order> orderList = orderService.getCurrentSessionOrders();
            orderList.stream()
                    .filter(order -> order.isActive() && order.getOperation() == Order.Operation.BUY)
                    .filter(orderBuy -> orderList.stream()
                            .filter(order -> order.isActive() &&
                                    order.getOperation() == Order.Operation.SELL &&
                                    order.getInstrument() == orderBuy.getInstrument() &&
                                    order.getPrice().compareTo(orderBuy.getPrice()) <= 0)
                            .mapToInt(Order::getQuantityRemainder)
                            .sum() >= orderBuy.getQuantityRemainder())
                    .findFirst().ifPresent(this::makeDeal);
        }
    }

    private void makeDeal(Order orderBuy) {
        List<Order> orderList = orderService.getCurrentSessionOrders();
        LOGGER.info("Sales orders that meet purchase conditions found");
        BigDecimal purchaseAmount = orderBuy.getPrice().multiply(BigDecimal.valueOf(orderBuy.getQuantityRemainder()));
        BigDecimal userAmount = userService.getUser(orderBuy.getUserName()).getAmount();
        if (userAmount.compareTo(purchaseAmount) < 0) {
            LOGGER.info("Cancellation of the agreement. The buyer does not have enough funds.");
            orderBuy.setActive(false);
            return;
        }
        while (orderBuy.getQuantityRemainder() != 0) {
            Order orderSell = orderList.stream()
                    .filter(o -> o.isActive() &&
                            o.getOperation() == Order.Operation.SELL &&
                            o.getInstrument() == orderBuy.getInstrument())
                    .findFirst().orElseThrow();
            formAgreement(orderBuy, orderSell);
        }
        orderBuy.setActive(false);
    }

    private void formAgreement(Order orderBuy, Order orderSell) {
        int quantityToBuy = orderBuy.getQuantityRemainder();
        int quantityToSell = orderSell.getQuantityRemainder();
        if (quantityToBuy <= quantityToSell) {
            orderBuy.subtractQuantity(quantityToBuy);
            orderSell.subtractQuantity(quantityToBuy);
        } else {
            orderBuy.subtractQuantity(quantityToSell);
            orderSell.subtractQuantity(quantityToSell);
        }
        if (orderSell.getQuantityRemainder() == 0) {
            orderSell.setActive(false);
        }
        int numberOfInstruments = quantityToBuy - orderBuy.getQuantityRemainder();
        BigDecimal dealAmount = orderSell.getPrice().multiply(BigDecimal.valueOf(numberOfInstruments));
        User customer = userService.getUser(orderBuy.getUserName());
        User salesman = userService.getUser(orderSell.getUserName());
        customer.subtractAmount(dealAmount);
        salesman.addAmount(dealAmount);

        orderBuy.addAgreement(new Agreement(orderSell.getUserName(), orderSell.getOperation().name(),
                orderSell.getInstrument().name(), numberOfInstruments, orderSell.getPrice(), LocalDateTime.now()));
        orderSell.addAgreement(new Agreement(orderBuy.getUserName(), orderBuy.getOperation().name(),
                orderBuy.getInstrument().name(), numberOfInstruments, orderSell.getPrice(), LocalDateTime.now()));
    }

}
