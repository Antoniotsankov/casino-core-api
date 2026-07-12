package org.example.casinocoreapi.model;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.example.casinocoreapi.enums.UserStatus;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;
    private String username;
    private String country;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdAt;
}
