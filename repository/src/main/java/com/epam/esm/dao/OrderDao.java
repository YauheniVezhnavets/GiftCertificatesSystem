package com.epam.esm.dao;

import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import com.epam.esm.utils.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao implements EntityDao<Order> {

    private static final String USER = "user";
    private static final String FIND_ORDER_BY_ID = "SELECT o from Order o where o.id=:id and o.user = :user";
    public static final String ID = "id";


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    Paginator paginator;


    @Override
    public long create(Order order) {
        entityManager.persist(order);
        entityManager.flush();
        return order.getOrderId();
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public void delete(long id) {
        Order order = entityManager.find(Order.class, id);
        if (order != null) {
            entityManager.remove(order);
        }
    }

    public List<Order> findAllOrders(int currentPage, long userId) {

        User user = entityManager.find(User.class, userId);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        CriteriaQuery<Order> findAllQuery = criteria.where(criteriaBuilder.equal(root.get(USER), user));
        TypedQuery<Order> typedQuery = entityManager.createQuery(findAllQuery);
        paginator.paginateQuery(currentPage,typedQuery);

        return typedQuery.getResultList();

    }

    public Order findOrder(long userId, long orderId){

        User user = entityManager.find(User.class,userId);

        Optional <Order> optionalOrder = Optional.ofNullable(entityManager.createQuery(FIND_ORDER_BY_ID,Order.class)
                .setParameter(ID,orderId)
                .setParameter(USER, user)
                .getSingleResult());

        return optionalOrder.get();
    }
}
