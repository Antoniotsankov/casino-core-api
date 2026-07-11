package org.example.casinocoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request for depositing money into a player's wallet")
public class DepositRequest {

    @NotNull(message = "User ID is required")
    @Schema(
            description = "User identifier",
            example = "1"
    )
    private Long userId;

    @NotNull(message = "Amount is required")
    @Positive(message ="Amount must be greater than zero")
    @Schema(
            description = "Amount to deposit",
            example = "150.00"
    )
    private BigDecimal amount;
}
