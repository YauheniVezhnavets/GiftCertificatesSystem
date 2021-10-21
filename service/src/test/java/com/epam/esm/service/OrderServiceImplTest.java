package com.epam.esm.service;

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
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;

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


//    @Test
//    public void methodShouldReturnListOfOrders() {
//
//   //     when(orderRepository.findAllOrders(1,1L)).thenReturn(new ArrayList<>());
//        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
//
//        List<OrderDto> orders = orderServiceImpl.findOrders(1,1);
//
//        assertNotNull(orders);
//    }
//
//    @Test
//    public void methodShouldReturnOrder() throws ResourceNotFoundException {
//
//        when(userRepository.findById(1L)).thenReturn(optionalUser);
//       // when(orderRepository.findOrder(1L,1L)).thenReturn(testOrder);
//        when(orderMapper.mapToDto(testOrder)).thenReturn(testOrderDto);
//
//        OrderDto orderDto = orderServiceImpl.findOrder(1L,1L);
//
//        assertNotNull(orderDto);
//    }
//
//
//    @Test
//    public void methodShouldCreateOrderTest() {
//        long expected = 7L;
//        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
//        when(giftCertificateRepository.findById(anyLong())).thenReturn(optionalGiftCertificate);
//
//        Order order = new Order(new BigDecimal(30),LocalDateTime.now());
//
//       // when(orderRepository.save(order)).thenReturn(expected);
//        orderServiceImpl.createOrder(1L,1L);
//
//        verify(orderRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void methodShouldDeleteOrder() throws ResourceNotFoundException {
//
//        when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
//        doNothing().when(orderRepository).delete(optionalOrder.get());
//
//        orderServiceImpl.deleteOrder(1L);
//
//        verify(orderRepository, times(1)).delete(optionalOrder.get());
//
//    }

}
