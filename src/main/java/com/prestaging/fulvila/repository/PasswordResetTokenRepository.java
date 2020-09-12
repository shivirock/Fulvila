package com.prestaging.fulvila.repository;

import com.prestaging.fulvila.model.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {
    @Query(value = "SELECT * FROM password_reset_token WHERE email = ?1 AND token =?2", nativeQuery = true)
    PasswordResetToken findByEmailAndToken(String email, String token);
}
