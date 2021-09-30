package com.epam.esm.dao;

import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entities.Order;
import com.epam.esm.utils.Paginator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@ExtendWith(SpringExtension.class)
@DirtiesContext
@ContextConfiguration(classes = { OrderDao.class, Paginator.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class OrderDaoTest {


    private List<Order> EXPECTED_TAGS = List.of(
            new Order(1L, new BigDecimal( 100), LocalDateTime.of(2021,9,23, 3,12,15,156)),
            new Order(2L, new BigDecimal( 100), LocalDateTime.of(2021,9,24, 3,12,15,156)));


    @Autowired
    private OrderDao orderDao;



    @Test
    public void findById() {
        Optional<Order> expected = Optional.of(new Order(1L, new BigDecimal( 100),
                LocalDateTime.of(2021,9,23, 3,12,15,156)));
        Optional<Order> actual = orderDao.findById(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        List<Order> actual = orderDao.findAllOrders(1,1L);
        assertEquals(EXPECTED_TAGS, actual);
    }


    @Test
    public void deleteTagTest() {
        Optional<Order> createdTag = orderDao.findById(1L);
        orderDao.delete(1L);
        Optional<Order> emptyTag = orderDao.findById(1L);
        assertNotEquals(createdTag, emptyTag);
    }
}