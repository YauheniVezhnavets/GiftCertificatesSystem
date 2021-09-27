package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entities.Order;

import java.util.List;

public interface OrderService <T extends Order> {

    void createOrder (long userId, long giftCertificateId);

    List<OrderDto> findOrders (long userId);

    OrderDto findOrder(long userId, long orderId);

}
