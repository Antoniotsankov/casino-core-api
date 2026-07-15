package org.example.casinocoreapi.service;
import org.example.casinocoreapi.dto.*;
import org.example.casinocoreapi.enums.TransactionType;
import org.example.casinocoreapi.exception.InsufficientBalanceException;
import org.example.casinocoreapi.exception.WalletNotFoundException;
import org.example.casinocoreapi.model.Wallet;
import org.example.casinocoreapi.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // telling JUnit, for this test use Mockito
public class WalletServiceTest {
    @Mock // I create fake repo and transactionService
    private WalletRepository walletRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks // in the constructor it adds the TransactionService & WalletRepository as mocks
    private WalletService walletService;

    private static final Long USER_ID = 1L;
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(100);
    private static final BigDecimal DEPOSIT_AMOUNT = BigDecimal.valueOf(50);
    private static final BigDecimal WITHDRAW_AMOUNT = BigDecimal.valueOf(30);
    private static final BigDecimal BET_AMOUNT = BigDecimal.valueOf(20);
    private static final BigDecimal WIN_AMOUNT = BigDecimal.valueOf(40);
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setId(USER_ID);
        wallet.setBalance(INITIAL_BALANCE);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.of(wallet));
    }

    @Test // it's a test method
    void deposit_shouldIncreaseBalance() {
        // Arrange
        DepositRequest request = new DepositRequest(); // here i create as if like the clients sends us a request for UserId1 and amount to deposit of 50
        request.setUserId(USER_ID);
        request.setAmount(DEPOSIT_AMOUNT);

        when(walletRepository.save(wallet))
                .thenReturn(wallet);
        // Act
        WalletResponse response = walletService.deposit(request);
        // Assert
        assertEquals(
                INITIAL_BALANCE.add(DEPOSIT_AMOUNT),
                response.getBalance()
        );
        // Verify
        verify(walletRepository).save(wallet);

        verify(transactionService).createTransaction(
                wallet,
                DEPOSIT_AMOUNT,
                TransactionType.DEPOSIT
        );
    }
    @Test
    void withdraw_shouldDecreaseBalance() {
        // Arrange
        WithdrawRequest request = new WithdrawRequest();
        request.setUserId(USER_ID);
        request.setAmount(WITHDRAW_AMOUNT);

        when(walletRepository.save(wallet))
                .thenReturn(wallet);
        // Act
        WalletResponse response = walletService.withdraw(request);
        // Assert
        assertEquals(
                INITIAL_BALANCE.subtract(WITHDRAW_AMOUNT),
                response.getBalance()
        );
        // Verify
        verify(walletRepository).save(wallet);

        verify(transactionService).createTransaction(
                wallet,
                WITHDRAW_AMOUNT,
                TransactionType.WITHDRAW
        );
    }

    @Test
    void bet_shouldDecreaseBalance() {
        // Arrange
        BetRequest request = new BetRequest();
        request.setUserId(USER_ID);
        request.setAmount(BET_AMOUNT);

        when(walletRepository.save(wallet))
                .thenReturn(wallet);
        // Act
        WalletResponse response = walletService.bet(request);

        // Assert
        assertEquals(
                INITIAL_BALANCE.subtract(BET_AMOUNT),
                response.getBalance()
        );

        // Verify
        verify(walletRepository).save(wallet);

        verify(transactionService).createTransaction(
                wallet,
                BET_AMOUNT,
                TransactionType.BET
        );
    }

    @Test
    void win_shouldIncreaseBalance() {
        // Arrange
        WinRequest request = new WinRequest();
        request.setUserId(USER_ID);
        request.setAmount(WIN_AMOUNT);

        when(walletRepository.save(wallet))
                .thenReturn(wallet);
        // Act
        WalletResponse response = walletService.win(request);

        // Assert
        assertEquals(
                INITIAL_BALANCE.add(WIN_AMOUNT),
                response.getBalance()
        );

        // Verify
        verify(walletRepository).save(wallet);

        verify(transactionService).createTransaction(
                wallet,
                WIN_AMOUNT,
                TransactionType.WIN
        );
    }
    // Negative test cases
    @Test
    void deposit_shouldThrowException_WhenWalletNotFound() {
        DepositRequest request = new DepositRequest();
        request.setUserId(USER_ID);
        request.setAmount(DEPOSIT_AMOUNT);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                WalletNotFoundException.class,
                () -> walletService.deposit(request) // the lambda expression is saying "do this code and see if it throws WalletNotFoundException"
        );
    }

    @Test
    void withdraw_shouldThrowException_WhenInsufficientBalance() {
        WithdrawRequest request = new WithdrawRequest();
        request.setUserId(USER_ID);
        request.setAmount(BigDecimal.valueOf(150));

        assertThrows(
                InsufficientBalanceException.class,
                () -> walletService.withdraw(request)
        );
    }

    @Test
    void withdraw_shouldThrowException_WhenWalletNotFound() {
        WithdrawRequest request = new WithdrawRequest();
        request.setUserId(USER_ID);
        request.setAmount(WITHDRAW_AMOUNT);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                WalletNotFoundException.class,
                () -> walletService.withdraw(request)
        );
    }

    @Test
    void bet_shouldThrowException_WhenInsufficientBalance() {
        // Arrange
        BetRequest request = new BetRequest();
        request.setUserId(USER_ID);
        request.setAmount(BigDecimal.valueOf(150));
        // Act & Assert
        assertThrows(
                InsufficientBalanceException.class,
                () -> walletService.bet(request)
        );
    }

    @Test
    void bet_shouldThrowException_WhenWalletNotFound() {

        BetRequest request = new BetRequest();
        request.setUserId(USER_ID);
        request.setAmount(BET_AMOUNT);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                WalletNotFoundException.class,
                () -> walletService.bet(request)
        );
    }

    @Test
    void win_shouldThrowException_WhenWalletNotFound() {

        WinRequest request = new WinRequest();
        request.setUserId(USER_ID);
        request.setAmount(WIN_AMOUNT);

        when(walletRepository.findByUserId(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                WalletNotFoundException.class,
                () -> walletService.win(request)
        );
    }
}
