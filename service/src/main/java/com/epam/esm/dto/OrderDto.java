package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    private long orderId;

    private BigDecimal cost;

    private LocalDateTime purchaseDate;

    private GiftCertificate giftCertificate;

    public OrderDto(long orderDtoId, BigDecimal orderDtoCost, LocalDateTime purchase_date) {
    }


}
