package org.example.casinocoreapi.repository;

import org.example.casinocoreapi.enums.TransactionType;
import org.example.casinocoreapi.model.Transaction;
import org.example.casinocoreapi.model.Wallet;
import org.hibernate.internal.TransactionManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.casinocoreapi.enums.TransactionType;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByWalletId(Long walletId, Pageable pageable);
    Page<Transaction> findByWalletIdAndType(
            Long walletId,
            TransactionType type,
            Pageable pageable);
}
