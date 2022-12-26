package com.demo.broker.controller;

import com.demo.broker.dto.OrderDto;
import com.demo.broker.dto.OrderNewDto;
import com.demo.broker.exception.UserNotFoundException;
import com.demo.broker.model.Order;
import com.demo.broker.service.OrderService;
import com.demo.broker.service.UserService;
import com.demo.broker.utils.ErrorConverter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ErrorConverter errorConverter;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService,
                           UserService userService,
                           ErrorConverter errorConverter) {
        this.orderService = orderService;
        this.userService = userService;
        this.errorConverter = errorConverter;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody OrderNewDto orderDto,
                                 BindingResult bindingResult,
                                 HttpSession httpSession) {
        String userName = (String) httpSession.getAttribute("user");
        if (!userService.isUserExists(userName)) {
            LOGGER.warn("POST /api/order/add {}", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>("Authorization failed", HttpStatus.UNAUTHORIZED);
        }
        if (bindingResult.hasErrors()) {
            LOGGER.warn("POST /api/order/add {}", HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(
                    errorConverter.convertFieldErrorListToMap(bindingResult.getFieldErrors()));
        }
        try {
            orderService.add(orderDto, userName);
            LOGGER.info("POST /api/order/add {}", HttpStatus.OK);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            LOGGER.warn("POST /api/order/add {}", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/trading-session")
    public ResponseEntity<Void> changeTradingSession(@RequestBody final boolean session,
                                                     HttpSession httpSession) {
        if ("admin".equals(httpSession.getAttribute("user"))) {
            orderService.setActiveSession(session);
            LOGGER.info("POST /api/order/trading-session {}", HttpStatus.OK);
            return ResponseEntity.ok().build();
        } else {
            LOGGER.warn("POST /api/order/trading-session {}", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/get-orders-current-session")
    public ResponseEntity<?> getOrdersCurrentSession(HttpSession httpSession) {
        if ("admin".equals(httpSession.getAttribute("user"))) {
            List<OrderDto> currentSessionOrders = orderService.getCurrentSessionOrdersDto();
            LOGGER.info("GET /api/order/get-orders-current-session {}", HttpStatus.OK);
            return ResponseEntity.ok(currentSessionOrders);
        } else {
            LOGGER.warn("GET /api/order/get-orders-current-session {}", HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }
}
