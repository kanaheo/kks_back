package com.kks.kks_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders") // 💯 복수형
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private int price;

    private String address;
    private String recipient;
    private String phone;
}
