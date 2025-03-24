package com.kks.shop.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonProperty("email")  // JSON으로 변환할 때 속성명 지정
    private String email;

    @Column(nullable = false)
    @JsonProperty("password")  // JSON 변환 가능하도록 명시
    private String password;
}
