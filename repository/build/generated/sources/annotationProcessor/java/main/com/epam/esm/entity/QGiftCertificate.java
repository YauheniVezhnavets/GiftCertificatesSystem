package com.epam.esm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGiftCertificate is a Querydsl query type for GiftCertificate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGiftCertificate extends EntityPathBase<GiftCertificate> {

    private static final long serialVersionUID = -1913159371L;

    public static final QGiftCertificate giftCertificate = new QGiftCertificate("giftCertificate");

    public final NumberPath<Long> certificateId = createNumber("certificateId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final DateTimePath<java.time.LocalDateTime> lastUpdateDate = createDateTime("lastUpdateDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final SetPath<Tag, QTag> tags = this.<Tag, QTag>createSet("tags", Tag.class, QTag.class, PathInits.DIRECT2);

    public QGiftCertificate(String variable) {
        super(GiftCertificate.class, forVariable(variable));
    }

    public QGiftCertificate(Path<? extends GiftCertificate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGiftCertificate(PathMetadata metadata) {
        super(GiftCertificate.class, metadata);
    }

}

