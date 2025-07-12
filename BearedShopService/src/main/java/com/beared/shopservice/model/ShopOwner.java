package com.beared.shopservice.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shopOwner")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopOwner extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(unique = true, length = 15)
    private String cnic;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String passwordHash;
}
