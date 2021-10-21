package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto extends RepresentationModel <OrderDto>{

    private long orderId;

    private BigDecimal cost;

    private LocalDateTime purchaseDate;

    private GiftCertificate giftCertificate;

    public OrderDto(long orderDtoId, BigDecimal orderDtoCost, LocalDateTime purchase_date) {
    }

    public OrderDto(BigDecimal bigDecimal, LocalDateTime now) {
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
}
