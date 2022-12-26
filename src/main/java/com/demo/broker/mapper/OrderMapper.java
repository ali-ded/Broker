package com.demo.broker.mapper;

import com.demo.broker.dto.OrderDto;
import com.demo.broker.dto.OrderNewDto;
import com.demo.broker.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderNewDtoToOrder(OrderNewDto orderNewDto);
    OrderDto orderToOrderDto(Order order);
    List<OrderDto> orderListToOrderDtoList(List<Order> order);
}
