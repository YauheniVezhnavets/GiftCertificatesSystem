package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.OrderDtoMapper;
import com.epam.esm.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service ("orderService")
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final OrderDtoMapper orderDtoMapper;

    @Override
    @Transactional
    public void createOrder(long userId, long giftCertificateId) {
        Order newOrder = new Order();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            newOrder.setUser(optionalUser.orElseThrow(()
                    -> new ResourceNotFoundException(giftCertificateId, "Gift certificate with this id not found")));
            Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(giftCertificateId);
            if (optionalGiftCertificate.isPresent()) {
                newOrder.setCertificate(optionalGiftCertificate.get());
                newOrder.setCost(optionalGiftCertificate.get().getPrice());
            } else {
                throw new ResourceNotFoundException(giftCertificateId, "Gift certificate with this id not found");
            }
        }
        orderRepository.save(newOrder);
    }

    @Override
    public Page <OrderDto> findOrders(int currentPage,int pageSize, long userId) {
        User user = userRepository.getById(userId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId, "User with this id not found");
        }
        Pageable pageAndResultPerPage = PageRequest.of(currentPage, pageSize);
        Page<Order> orders = orderRepository.findAllByUser(user,pageAndResultPerPage);

        return new PageImpl<>(orders.stream().map(orderDtoMapper::mapToDto).collect(Collectors.toList()));
    }

    @Override
    public OrderDto findOrder(long userId, long orderId) {
        User user = userRepository.getById(userId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId, "User with this id not found");
        }
        Optional<Order> optionalOrder = orderRepository.findByUserAndOrderId(user, orderId);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException(orderId, "Order not found");
        }
        return orderDtoMapper.mapToDto(optionalOrder.get());
    }

    @Override
    @Transactional
    public void deleteOrder(long id) throws ResourceNotFoundException {
        if (orderRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id, "Order with this id not found");
        }
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);
    }
}
