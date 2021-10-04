package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao,
                            OrderMapper orderMapper) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public void createOrder(long userId, long giftCertificateId) {
        Order newOrder = new Order();
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isPresent()) {
            newOrder.setUser(optionalUser.get());
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(giftCertificateId);
            if (optionalGiftCertificate.isPresent()) {
                newOrder.setCertificate(optionalGiftCertificate.get());
                newOrder.setCost(optionalGiftCertificate.get().getPrice());
            } else {
                throw new ResourceNotFoundException(giftCertificateId);
            }
        } else {
            throw new ResourceNotFoundException(userId);
        }
        orderDao.create(newOrder);
    }

    @Override
    public List<OrderDto> findOrders(int currentPage, long userId) {
        if (userDao.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        List<Order> orders = orderDao.findAllOrders(currentPage, userId);
        return orders.stream().map(orderMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto findOrder(long userId, long orderId) {
        if (userDao.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        Order order = orderDao.findOrder(userId, orderId);
        return orderMapper.mapToDto(order);
    }

    @Override
    @Transactional
    public void deleteOrder(long id) throws ResourceNotFoundException {
        if (orderDao.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        Order order = orderDao.findById(id).get();
        orderDao.delete(order);
    }
}
