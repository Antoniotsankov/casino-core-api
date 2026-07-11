package org.example.casinocoreapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.casinocoreapi.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

}
