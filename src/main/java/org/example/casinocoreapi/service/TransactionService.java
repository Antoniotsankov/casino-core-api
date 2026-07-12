package org.example.casinocoreapi.service;

import org.example.casinocoreapi.dto.PageResponse;
import org.example.casinocoreapi.dto.TransactionResponse;
import org.example.casinocoreapi.enums.TransactionType;
import org.example.casinocoreapi.model.Transaction;
import org.example.casinocoreapi.model.Wallet;
import org.example.casinocoreapi.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public PageResponse<TransactionResponse> getTransactionsByWalletId(
            Long walletId,
            TransactionType type,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Transaction> transactions;

        if (type != null) {
            transactions = transactionRepository.findByWalletIdAndType(
                    walletId,
                    type,
                    pageable
            );
        } else {
            transactions = transactionRepository.findByWalletId(
                    walletId,
                    pageable
            );
        }

        PageResponse<TransactionResponse> response = new PageResponse<>();

        response.setContent(
                transactions.getContent()
                        .stream()
                        .map(this::buildTransactionResponse)
                        .toList()
        );

        response.setPage(transactions.getNumber());
        response.setSize(transactions.getSize());
        response.setTotalElements(transactions.getTotalElements());
        response.setTotalPages(transactions.getTotalPages());

        return response;
    }

    private TransactionResponse buildTransactionResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setCreatedAt(transaction.getCreatedAt());

        return response;
    }

    public void createTransaction(
            Wallet wallet,
            BigDecimal amount,
            TransactionType type) {

        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setWallet(wallet);
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}