package com.ainterview.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User {
    private String id;
    private String name;
    private String email;
    private String password;

    @Builder.Default
    private Role role = Role.USER;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();


}
