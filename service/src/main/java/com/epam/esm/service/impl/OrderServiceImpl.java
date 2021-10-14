package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service ("orderService")
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public void createOrder(long userId, long giftCertificateId) {
        Order newOrder = new Order();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            newOrder.setUser(optionalUser.get());
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(giftCertificateId);
            if (optionalGiftCertificate.isPresent()) {
                newOrder.setCertificate(optionalGiftCertificate.get());
                newOrder.setCost(optionalGiftCertificate.get().getPrice());
            } else {
                throw new ResourceNotFoundException(giftCertificateId);
            }
        } else {
            throw new ResourceNotFoundException(userId);
        }
        orderRepository.save(newOrder);
    }

    @Override
    public List<OrderDto> findOrders(int currentPage, long userId) {
        User user = userRepository.getById(userId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        Pageable pageAndResultPerPage = PageRequest.of(currentPage, 5);
        List <Order> orders = orderRepository.findAllByUser(user);

        return orders.stream().map(orderMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto findOrder(long userId, long orderId) {
        User user = userRepository.getById(userId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }
        Optional<Order> optionalOrder = orderRepository.findByUserAndOrderId(user, orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException(orderId);
        }
        return orderMapper.mapToDto(optionalOrder.get());
    }

    @Override
    @Transactional
    public void deleteOrder(long id) throws ResourceNotFoundException {
        if (orderRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);
    }
}
