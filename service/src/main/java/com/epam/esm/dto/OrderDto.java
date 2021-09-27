package com.epam.esm.dto;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto extends RepresentationModel <Order> {

    private long orderId;

    private BigDecimal cost;

    private LocalDateTime purchaseDate;

    private GiftCertificate giftCertificate;

    public OrderDto(BigDecimal cost, LocalDateTime purchaseDate) {
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public OrderDto(long orderId, BigDecimal cost, LocalDateTime purchaseDate) {
        this.orderId = orderId;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public long getOrderId() {
        return orderId;
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

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDto orderDto = (OrderDto) o;

        if (getOrderId() != orderDto.getOrderId()) return false;
        if (getCost() != null ? !getCost().equals(orderDto.getCost()) : orderDto.getCost() != null) return false;
        if (getPurchaseDate() != null ? !getPurchaseDate().equals(orderDto.getPurchaseDate()) : orderDto.getPurchaseDate() != null)
            return false;
        return getGiftCertificate() != null ? getGiftCertificate().equals(orderDto.getGiftCertificate()) : orderDto.getGiftCertificate() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getOrderId() ^ (getOrderId() >>> 32));
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + (getPurchaseDate() != null ? getPurchaseDate().hashCode() : 0);
        result = 31 * result + (getGiftCertificate() != null ? getGiftCertificate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", cost=" + cost +
                ", purchaseDate=" + purchaseDate +
                ", giftCertificate=" + giftCertificate +
                '}';
    }
}
