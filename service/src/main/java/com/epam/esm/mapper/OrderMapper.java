package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId(order.getOrderId());
        orderDto.setCost(order.getCost());
        orderDto.setPurchaseDate(order.getPurchaseDate());
        orderDto.setGiftCertificate(order.getCertificate());

        return orderDto;
    }
}
