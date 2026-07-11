package org.example.casinocoreapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.casinocoreapi.dto.PageResponse;
import org.example.casinocoreapi.dto.TransactionResponse;
import org.example.casinocoreapi.enums.TransactionType;
import org.example.casinocoreapi.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Tag(
        name = "Transaction API",
        description = "Operations related to transaction history"
)
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/wallet/{walletId}")
    @Operation(
            summary = "Get transaction history",
            description = "Returns wallet transactions with optional filtering and pagination."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<PageResponse<TransactionResponse>> getTransactionsByWalletId(

            @PathVariable Long walletId,

            @RequestParam(required = false)
            TransactionType type,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByWalletId(
                        walletId,
                        type,
                        page,
                        size
                )
        );
    }
}
