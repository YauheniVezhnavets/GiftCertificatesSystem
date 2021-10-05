package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public OrderDto mapToDto(Order order) {
        long orderDtoId = order.getOrderId();
        BigDecimal orderDtoCost = order.getCost();
        LocalDateTime purchase_date = order.getPurchaseDate();
        GiftCertificate giftCertificate = order.getCertificate();

        OrderDto orderDto = new OrderDto(orderDtoId, orderDtoCost, purchase_date);
        orderDto.setGiftCertificate(giftCertificate);

        return orderDto;
    }
}
