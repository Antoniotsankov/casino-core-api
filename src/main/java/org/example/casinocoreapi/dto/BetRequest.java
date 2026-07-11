package org.example.casinocoreapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request for placing a casino bet")
public class BetRequest {
    @Schema(
            description = "User identifier",
            example = "1"
    )
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(
            description = "Bet amount",
            example = "50.00"
    )
    @NotNull
    @Positive
    private BigDecimal amount;
}
