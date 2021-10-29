package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_order")
public class Order implements Identifiable {

    @Id
    @SequenceGenerator(
            name = "user_order_id_seq",
            sequenceName = "user_order_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_order_id_seq"
    )
    @Column(name = "id")
    private long orderId;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    private GiftCertificate certificate;

    @PrePersist
    private void onPrePersist() {
        setPurchaseDate(LocalDateTime.now());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (getOrderId() != order.getOrderId()) return false;
        if (getCost() != null ? !getCost().equals(order.getCost()) : order.getCost() != null) return false;
        if (getPurchaseDate() != null ? !getPurchaseDate().equals(order.getPurchaseDate()) : order.getPurchaseDate() != null)
            return false;
        if (getUser() != null ? !getUser().equals(order.getUser()) : order.getUser() != null) return false;
        return getCertificate() != null ? getCertificate().equals(order.getCertificate()) : order.getCertificate() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getOrderId() ^ (getOrderId() >>> 32));
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + (getPurchaseDate() != null ? getPurchaseDate().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getCertificate() != null ? getCertificate().hashCode() : 0);
        return result;
    }
}
