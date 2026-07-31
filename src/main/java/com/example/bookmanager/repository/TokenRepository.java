package com.example.bookmanager.repository;

import com.example.bookmanager.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Long, Token> {

    Optional<Token> findByToken(String token);
}
