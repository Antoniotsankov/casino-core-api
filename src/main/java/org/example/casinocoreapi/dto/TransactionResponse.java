package org.example.casinocoreapi.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.casinocoreapi.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Response containing transaction information")
public class TransactionResponse {

    @Schema(description = "Transaction identifier", example = "15")
    private Long id;

    @Schema(description = "Transaction amount", example = "100.00")
    private BigDecimal amount;

    @Schema(description = "Transaction type", example = "BET")
    private TransactionType type;

    @Schema(description = "Transaction creation date and time", example = "2026-07-09T13:15:30")
    private LocalDateTime createdAt;
}
