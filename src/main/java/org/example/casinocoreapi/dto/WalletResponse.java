package org.example.casinocoreapi.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.casinocoreapi.enums.Currency;
import org.example.casinocoreapi.enums.WalletStatus;
import org.example.casinocoreapi.model.Wallet;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Response containing wallet information")
public class WalletResponse {

    @Schema(description = "Wallet identifier", example = "1")
    private Long id;

    @Schema(description = "Current wallet balance", example = "1500.00")
    private BigDecimal balance;

    @Schema(description = "Wallet currency", example = "USD")
    private Currency currency;

    @Schema(description = "Current wallet status", example = "ACTIVE")
    private WalletStatus status;
}
