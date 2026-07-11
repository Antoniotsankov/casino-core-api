package org.example.casinocoreapi.repository;

import org.example.casinocoreapi.enums.UserStatus;
import org.example.casinocoreapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);
    List<User> findByCountryAndStatus(String country, UserStatus status);

    boolean existsByMemberId(String memberId);
}