package com.epam.esm.entities;


import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_order")
public class Order extends RepresentationModel <Order> implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long orderId;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
            (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    private GiftCertificate certificate;

    public Order (){
    }

    public Order(BigDecimal cost, LocalDateTime purchaseDate) {
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public Order(long orderId, BigDecimal cost, LocalDateTime purchaseDate) {
        this.orderId = orderId;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public GiftCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    @PrePersist
    private void onPrePersist() {
        setPurchaseDate(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", cost=" + cost +
                ", purchaseDate=" + purchaseDate +
                ", user=" + user +
                ", certificate=" + certificate +
                '}';
    }
}
