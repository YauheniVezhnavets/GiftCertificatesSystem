package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderDtpMapperTest {

    private final OrderDtoMapper orderDtoMapper = new OrderDtoMapper();
    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    Order order = new Order(1L, new BigDecimal("30.00"),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));
    OrderDto expectedOrderDto = new OrderDto();
    OrderDto invalidOrderDto = new OrderDto(1L, new BigDecimal("50.00"),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    @Test
    void testShouldMapOrderToOrderDto() {

        OrderDto actualOrderDto = orderDtoMapper.mapToDto(order);
        expectedOrderDto.setOrderId(1L);
        expectedOrderDto.setCost(new BigDecimal("30.00"));
        expectedOrderDto.setPurchaseDate(LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

        assertEquals(expectedOrderDto, actualOrderDto);
    }

    @Test
    void testShouldMapTagToTagDtoNotEquals() {

        OrderDto actualOrderDto = orderDtoMapper.mapToDto(order);

        assertNotEquals(invalidOrderDto, actualOrderDto);
    }
}
