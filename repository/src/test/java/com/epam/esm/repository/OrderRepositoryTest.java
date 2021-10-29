package com.epam.esm.repository;


import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OrderRepository.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void whenFindById_thenReturnOrder() {
        // given
        Order order = new Order(new BigDecimal("30.00"),LocalDateTime.now());
        entityManager.persist(order);


        // when
        Order foundOrder = orderRepository.findById(order.getOrderId()).get();

        // then
        assertEquals(order,foundOrder);
    }


    @Test
    @Transactional
    public void whenDelete_thenReturnNull() {
        // given
        Order order = new Order(new BigDecimal("30.00"), LocalDateTime.now());
        entityManager.persist(order);

        // when
        orderRepository.delete(order);

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrderId());

        // then
        assertEquals(Optional.empty(),optionalOrder);
    }
}