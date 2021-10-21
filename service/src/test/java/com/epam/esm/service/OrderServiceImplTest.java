package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.OrderDtoMapper;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceImplTest {

    private static Order testOrder;
    private static Optional<Order> optionalOrder;
    private static OrderDto testOrderDto;
    private static Optional<OrderDto> optionalOrderDto;
    private static User testUser;
    private static Optional<User> optionalUser;
    private static GiftCertificate testGiftCertificate;
    private static Optional<GiftCertificate> optionalGiftCertificate;


    @Mock
    UserRepository userRepository;

    @Mock
    GiftCertificateRepository giftCertificateRepository;

    @Mock
    OrderDtoMapper orderDtoMapper;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testOrder = Mockito.mock(Order.class);
        optionalOrder = Optional.of(testOrder);
        testOrderDto = Mockito.mock(OrderDto.class);
        optionalOrderDto = Optional.of(testOrderDto);
        testUser = Mockito.mock(User.class);
        optionalUser = Optional.of(testUser);
        testGiftCertificate = Mockito.mock(GiftCertificate.class);
        optionalGiftCertificate = Optional.of(testGiftCertificate);
    }

    private final Order order = new Order(new BigDecimal(30.00),LocalDateTime.now());
    private final OrderDto orderDto = new OrderDto(new BigDecimal(30.00),LocalDateTime.now());


    @Test
    public void methodShouldReturnListOfOrders() {

        Pageable pageAndResultPerPage = PageRequest.of(0, 1);
        when(userRepository.getById(1L)).thenReturn(testUser);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(orderRepository.findAllByUser(testUser,pageAndResultPerPage)).thenReturn(new PageImpl<>(List.of(order)));
        when(orderDtoMapper.mapToDto(order)).thenReturn(orderDto);

        Page<OrderDto> orders = orderServiceImpl.findOrders(0, 1,1L);

        assertNotNull(orders);
    }

    @Test
    public void methodShouldReturnOrder() throws ResourceNotFoundException {

        when(userRepository.getById(1L)).thenReturn(testUser);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUserAndOrderId(testUser,1L)).thenReturn(Optional.of(order));
        when(orderDtoMapper.mapToDto(order)).thenReturn(orderDto);

        OrderDto orderDto = orderServiceImpl.findOrder(1L,1L);

        assertNotNull(orderDto);

    }

    @Test
    public void methodShouldCreateOrderTest() {

        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        when(giftCertificateRepository.findById(anyLong())).thenReturn(optionalGiftCertificate);

        when(orderRepository.save(order)).thenReturn(any());
        orderServiceImpl.createOrder(1L,1L);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    public void methodShouldDeleteOrder() throws ResourceNotFoundException {

        when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
        doNothing().when(orderRepository).delete(optionalOrder.get());

        orderServiceImpl.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(optionalOrder.get());

    }

    @Test
    public void methodShouldThrowExceptionWhenOrderIsEmpty() {


        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            orderServiceImpl.deleteOrder(1L);

        });
    }

    @Test
    public void methodShouldThrowExceptionWhenUserNotFound() {


        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            orderServiceImpl.findOrders(1,1,1L);

        });
    }

    @Test
    public void methodShouldThrowExceptionWhenUserNotFoundInMethodCreateOrder() {


        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            orderServiceImpl.createOrder(1,1);

        });
    }

    @Test
    public void methodShouldThrowExceptionWhenUserNotFoundInMethodFindOrder() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            orderServiceImpl.findOrder(1,1);

        });
    }
}
