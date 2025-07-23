package com.beared.shopservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarberServices extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(nullable = false, length = 255)
    private String serviceName;

    @Column(nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "services")
    private List<Shop2Service> shopServices = new ArrayList<>();
}
