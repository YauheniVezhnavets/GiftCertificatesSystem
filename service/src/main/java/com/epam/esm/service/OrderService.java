package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;


/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @author Yauheni Vezhnavets
 * @param <T> has to implement {@link Order} interface
 * @see Order
 */
public interface OrderService <T extends Order> {

    /**
     * This method create new order.
     *
     *
     */

    void createOrder (long userId, long giftCertificateId);


    /**
     * This method return existing orders connected with user.
     * @return list of{@link OrderDto}
     */
    Page <OrderDto> findOrders (int currentPage, int pageSize, long userId);


    /**
     * This method return order by his id and user id.
     *
     * @return {@link Order}
     *
     */
    OrderDto findOrder(long userId, long orderId);



    /**
     * This method delete order by his id.
     *
     * @return {@link Order}
     *
     */

   void deleteOrder(long id);
}
