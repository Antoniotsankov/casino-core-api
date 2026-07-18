package org.example.casinocoreapi.service;
import org.example.casinocoreapi.dto.PageResponse;
import org.example.casinocoreapi.dto.TransactionResponse;
import org.example.casinocoreapi.enums.TransactionType;
import org.example.casinocoreapi.model.Transaction;
import org.example.casinocoreapi.model.Wallet;
import org.example.casinocoreapi.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private TransactionService transactionService;

    private static final Long WALLET_ID = 1L;
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    private static final TransactionType TYPE = TransactionType.DEPOSIT;

    private Wallet wallet;
    private Transaction transaction;

    @BeforeEach
    void setUP() {
        wallet = new Wallet();
        wallet.setId(WALLET_ID);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setWallet(wallet);
        transaction.setAmount(AMOUNT);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getTransactionByWalletId_shouldReturnTrasnactions() {
        // Arrange - create a page containing our transaction
        Page<Transaction> page = new PageImpl<>(List.of(transaction));
        // Mock - when the repository is called, return the page
        when(transactionRepository.findByWalletId(
                WALLET_ID, PageRequest.of(0, 10))).thenReturn(page);
        // Act execute the method
        PageResponse<TransactionResponse> response = transactionService.getTransactionsByWalletId(
                WALLET_ID, null, 0, 10);
        // Assert - verify that one TransactionResponse is returned
        assertEquals(1, response.getContent().size());
        // Assert - verify the returned Transaction data
        assertEquals(AMOUNT, response.getContent().get(0).getAmount());
        assertEquals(TransactionType.DEPOSIT,
                response.getContent().get(0).getType());
        // Verify - check that the repository method was called
        verify(transactionRepository)
                .findByWalletId(
                        WALLET_ID,
                        PageRequest.of(0, 10));
    }

    @Test
    void getTransactionByWalletId_shouldReturnTransactionsByType() {
        Page<Transaction> page = new PageImpl<>(List.of(transaction));

        when(transactionRepository.findByWalletIdAndType(
                WALLET_ID, TYPE, PageRequest.of(0, 10))).thenReturn(page);

        PageResponse<TransactionResponse> response = transactionService.getTransactionsByWalletId(
                WALLET_ID, TYPE, 0, 10);

        assertEquals(1, response.getContent().size());

        assertEquals(AMOUNT, response.getContent().get(0).getAmount());
        assertEquals(TYPE, response.getContent().get(0).getType());

        verify(transactionRepository)
                .findByWalletIdAndType(
                        WALLET_ID,
                        TYPE,
                        PageRequest.of(0, 10));
    }

    @Test
    void createTransaction_shouldSaveTransaction() {
        transactionService.createTransaction(wallet, AMOUNT, TYPE);
        // Arrange capture the Transaction passed to save()
        ArgumentCaptor<Transaction> transactionCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        // Verify check that save was called
        verify(transactionRepository).save(transactionCaptor.capture());
        // Get the captured Transaction
        Transaction savedTransaction = transactionCaptor.getValue();
        // Assert verify the Transaction data
        assertEquals(wallet, savedTransaction.getWallet());
        assertEquals(AMOUNT, savedTransaction.getAmount());
        assertEquals(TYPE, savedTransaction.getType());
        assertNotNull(savedTransaction.getCreatedAt());
    }

    @Test
    void getTransactionsByWalletId_shouldReturnEmptyPage() {
        // Arrange
        Page<Transaction> page = new PageImpl<>(List.of());

        when(transactionRepository.findByWalletId(
                WALLET_ID,
                PageRequest.of(0, 10)))
                .thenReturn(page);
        // Act
        PageResponse<TransactionResponse> response =
                transactionService.getTransactionsByWalletId(
                        WALLET_ID,
                        null,
                        0,
                        10);
        // Assert
        assertTrue(response.getContent().isEmpty());
        // Verify
        verify(transactionRepository)
                .findByWalletId(
                        WALLET_ID,
                        PageRequest.of(0, 10));
    }
}

