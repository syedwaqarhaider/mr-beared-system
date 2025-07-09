package com.beared.userservice.repository;
import com.beared.userservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmail(String email);

    Optional<UserAccount> findByPhoneNumber(String phoneNumber);

    Optional<UserAccount> findByAuthCode(String authCode);
}
