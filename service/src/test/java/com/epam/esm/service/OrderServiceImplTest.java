package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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
    UserDao userDao;

    @Mock
    GiftCertificateDao giftCertificateDao;

    @Mock
    OrderMapper orderMapper;

    @Mock
    private OrderDao orderDao;

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


    @Test
    public void methodShouldReturnListOfOrders() {

        when(orderDao.findAllOrders(1,1L)).thenReturn(new ArrayList<>());
        when(userDao.findById(anyLong())).thenReturn(optionalUser);

        List<OrderDto> orders = orderServiceImpl.findOrders(1,1);

        assertNotNull(orders);
    }

    @Test
    public void methodShouldReturnOrder() throws ResourceNotFoundException {

        when(userDao.findById(1L)).thenReturn(optionalUser);
        when(orderDao.findOrder(1L,1L)).thenReturn(testOrder);
        when(orderMapper.mapToDto(testOrder)).thenReturn(testOrderDto);

        OrderDto orderDto = orderServiceImpl.findOrder(1L,1L);

        assertNotNull(orderDto);
    }


    @Test
    public void methodShouldCreateOrderTest() {
        long expected = 7L;
        when(userDao.findById(anyLong())).thenReturn(optionalUser);
        when(giftCertificateDao.findById(anyLong())).thenReturn(optionalGiftCertificate);

        Order order = new Order(new BigDecimal(30),LocalDateTime.now());

        when(orderDao.create(order)).thenReturn(expected);
        orderServiceImpl.createOrder(1L,1L);

        verify(orderDao, times(1)).create(any());
    }

    @Test
    public void methodShouldDeleteOrder() throws ResourceNotFoundException {

        when(orderDao.findById(anyLong())).thenReturn(optionalOrder);
        doNothing().when(orderDao).delete(optionalOrder.get());

        orderServiceImpl.deleteOrder(1L);

        verify(orderDao, times(1)).delete(optionalOrder.get());

    }

}
