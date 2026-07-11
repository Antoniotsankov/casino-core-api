package org.example.casinocoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request for withdrawing money from a wallet")
public class WithdrawRequest {

    @Schema(
            description = "User identifier",
            example = "1"
    )
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(
            description = "Amount to withdraw",
            example = "100.00"
    )
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
}
