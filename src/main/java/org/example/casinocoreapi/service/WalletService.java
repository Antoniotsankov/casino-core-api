package org.example.casinocoreapi.service;

import org.example.casinocoreapi.dto.*;
import org.example.casinocoreapi.enums.Currency;
import org.example.casinocoreapi.enums.TransactionType;
import org.example.casinocoreapi.enums.WalletStatus;
import org.example.casinocoreapi.exception.InsufficientBalanceException;
import org.example.casinocoreapi.exception.WalletNotFoundException;
import org.example.casinocoreapi.model.User;
import org.example.casinocoreapi.model.Wallet;
import org.example.casinocoreapi.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final TransactionService transactionService;


    public WalletService(WalletRepository walletRepository, TransactionService transactionService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
    }

    public Wallet createWallet(User user) {

        Wallet wallet = new Wallet();

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency(determineCurrency((user.getCountry())));
        wallet.setStatus(WalletStatus.ACTIVE);
        wallet.setUser(user);

        walletRepository.save(wallet);
        return wallet;

    }

    public Wallet getWalletByUserId(Long userId) {

        Optional<Wallet> wallet = walletRepository.findByUserId(userId);
        return wallet.orElseThrow(
                () -> new WalletNotFoundException("Wallet not found")
        );
    }

    public WalletResponse getWalletResponseByUserId(Long userId) {

        Wallet wallet = getWalletByUserId(userId);

        return buildWalletResponse(wallet);
    }

    private Currency determineCurrency(String country) {

        if (country.equalsIgnoreCase("Bulgaria")) {
            return Currency.BGN;
        }
        if (country.equalsIgnoreCase("Germany")) {
            return Currency.EUR;
        }
        if (country.equalsIgnoreCase("United Kingdom")) {
            return Currency.GBP;
        }
        if (country.equalsIgnoreCase("USA")) {
            return Currency.USD;
        }

        return Currency.EUR;
    }

    private WalletResponse buildWalletResponse(Wallet wallet) {

        WalletResponse response = new WalletResponse();

        response.setId(wallet.getId());
        response.setBalance(wallet.getBalance());
        response.setCurrency(wallet.getCurrency());
        response.setStatus(wallet.getStatus());

        return response;
    }

    private WalletResponse increaseBalance(Wallet wallet, BigDecimal amount, TransactionType type) {
        wallet.setBalance(
                wallet.getBalance().add(amount));

        Wallet updatedWallet = walletRepository.save(wallet);


        transactionService.createTransaction(
                updatedWallet,
                amount,
                type
        );

        return buildWalletResponse(updatedWallet);
    }

    private WalletResponse decreaseBalance(Wallet wallet, BigDecimal amount, TransactionType type) {

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        wallet.setBalance(
                wallet.getBalance().subtract(amount)
        );

        Wallet updatedWallet = walletRepository.save(wallet);

        transactionService.createTransaction(
                updatedWallet,
                amount,
                type
        );

        return buildWalletResponse(updatedWallet);
    }

    public WalletResponse deposit(DepositRequest request) {

        Wallet wallet = getWalletByUserId(request.getUserId());

        return increaseBalance(
                wallet,
                request.getAmount(),
                TransactionType.DEPOSIT
        );
    }

    public WalletResponse withdraw(WithdrawRequest request) {

        Wallet wallet = getWalletByUserId(request.getUserId());

        return decreaseBalance(
                wallet,
                request.getAmount(),
                TransactionType.WITHDRAW
        );
    }

    public WalletResponse bet(BetRequest request) {

        Wallet wallet = getWalletByUserId(request.getUserId());

        return decreaseBalance(
                wallet,
                request.getAmount(),
                TransactionType.BET
        );
    }

    public WalletResponse win(WinRequest request) {

        Wallet wallet = getWalletByUserId(request.getUserId());

        return increaseBalance(
                wallet,
                request.getAmount(),
                TransactionType.WIN
        );
    }
}


