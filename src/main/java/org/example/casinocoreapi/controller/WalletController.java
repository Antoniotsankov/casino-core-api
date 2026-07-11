package org.example.casinocoreapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.casinocoreapi.dto.*;
import org.example.casinocoreapi.model.Wallet;
import org.example.casinocoreapi.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //казва на Spring, че класът приема HTTP заявки и връща JSON
@RequestMapping("/wallets") //задава общия URL префикс за всички методи в този Controller.
@Tag(
        name = "Wallet API",
        description = "Operations related to wallet management"
)
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Get wallet",
            description = "Returns the wallet for the specified user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet found"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<WalletResponse> getWalletByUserId(
            @PathVariable Long userId) {

        WalletResponse wallet = walletService.getWalletResponseByUserId(userId);

        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/deposit")
    @Operation(
            summary = "Deposit money",
            description = "Adds money to the player's wallet."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deposit completed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<WalletResponse> deposit(
            @Valid @RequestBody DepositRequest request) {
        WalletResponse wallet = walletService.deposit(request);

        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/withdraw")
    @Operation(
            summary = "Withdraw money",
            description = "Withdraws money from the player's wallet."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Withdrawal completed successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient balance or invalid request"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<WalletResponse> withdraw (
            @Valid @RequestBody WithdrawRequest request) {
        WalletResponse wallet = walletService.withdraw(request);

        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/bet")
    @Operation(
            summary = "Place bet",
            description = "Places a casino bet and deducts the player's balance."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bet placed successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient balance or invalid request"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
            public ResponseEntity<WalletResponse> bet (
            @Valid @RequestBody BetRequest request) {
        WalletResponse wallet = walletService.bet(request);

                return ResponseEntity.ok(wallet);
    }

    @PostMapping("/win")
    @Operation(
            summary = "Credit winnings",
            description = "Credits the player's winnings to the wallet."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Winnings credited successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<WalletResponse> win (
        @Valid @RequestBody WinRequest request) {
        WalletResponse wallet = walletService.win(request);

        return ResponseEntity.ok(wallet);
    }

}
