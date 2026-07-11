package org.example.casinocoreapi.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.casinocoreapi.enums.Currency;
import org.example.casinocoreapi.enums.WalletStatus;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private WalletStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
