package com.beared.shopservice.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(nullable = false, length = 255)
    private String shopName;

    @Column(length = 500)
    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private ShopOwner owner;

    @Column(nullable = false, length = 255)
    private String shopAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, columnDefinition = "CHAR")
    private char status;

    private Double avgRating=0.0;

    @OneToMany(mappedBy = "shop")
    private List<Shop2Service> shopServices = new ArrayList<>();

}


