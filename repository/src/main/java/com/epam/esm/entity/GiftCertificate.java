package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate implements Identifiable {

    @Id
    @SequenceGenerator(
            name = "gift_certificate_id_seq",
            sequenceName = "gift_certificate_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gift_certificate_id_seq"
    )
    @Column(name = "id")
    private long certificateId;

    @NotBlank
    @Size(min = 3, max = 50, message = "Name should have minimum 3 symbols and maximum 50")
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(min = 5, max = 100, message = "Description should have minimum 5 symbols and maximum 100")
    @Column(name = "description")
    private String description;

    @DecimalMin(value = "1.0", message = "Price should be minimum 1")
    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Min(value = 1, message = "Duration should be minimum 1")
    @Column(name = "duration")
    private int duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    @NotNull
    private Set<Tag> tags;

    @JsonIgnore
    @OneToMany(mappedBy = "certificate")
    private transient Set<Order> orders;

    @PrePersist
    private void onPrePersist() {
        setCreateDate(LocalDateTime.now());
        setLastUpdateDate(LocalDateTime.now());
    }

    public GiftCertificate(String name, String description, BigDecimal price,
                           int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public GiftCertificate(long certificateId, String name, String description, BigDecimal price,
                           int duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.certificateId = certificateId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    @PreUpdate
    private void onPreUpdate() {
        setLastUpdateDate(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (getCertificateId() != that.getCertificateId()) return false;
        if (getDuration() != that.getDuration()) return false;
        if (isActive() != that.isActive()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(that.getCreateDate()) : that.getCreateDate() != null)
            return false;
        if (getLastUpdateDate() != null ? !getLastUpdateDate().equals(that.getLastUpdateDate()) : that.getLastUpdateDate() != null)
            return false;
        if (getTags() != null ? !getTags().equals(that.getTags()) : that.getTags() != null) return false;
        return getOrders() != null ? getOrders().equals(that.getOrders()) : that.getOrders() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getCertificateId() ^ (getCertificateId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + getDuration();
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getLastUpdateDate() != null ? getLastUpdateDate().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        result = 31 * result + (getOrders() != null ? getOrders().hashCode() : 0);
        return result;
    }
}
