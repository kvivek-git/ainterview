package com.ainterview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder.Default
    private Role role = Role.USER;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();


}
