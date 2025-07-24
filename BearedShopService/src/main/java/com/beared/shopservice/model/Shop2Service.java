package com.beared.shopservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shop2service",
        uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "service_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop2Service extends Base{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private BarberServices services;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private Boolean active = true;

}
