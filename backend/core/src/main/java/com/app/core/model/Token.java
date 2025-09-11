package com.app.core.model;

import com.app.core.config.AppConfig;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Token {
    private String tokenId;
    private int userId;
    private Instant requestDate;
    private Instant expirationTime;
    
    public Token(int userId) {
        this.tokenId = UUID.randomUUID().toString();
        this.userId = userId;
        this.requestDate = Instant.now();
        this.expirationTime = Instant.now().plusSeconds(AppConfig.getTokenInitialMinutes() * 60L);
    }
    
    public void extend() {
        this.expirationTime = Instant.now().plusSeconds(AppConfig.getTokenExtensionMinutes() * 60L);
    }
    
    public boolean isValid() {
        return Instant.now().isBefore(expirationTime);
    }
    
    public String getTokenInfo() {
        return String.format(
            "Token ID: %s | User ID: %d | Request Date: %s | Expiration: %s",
            tokenId,
            userId,
            formatInstant(requestDate),
            formatInstant(expirationTime)
        );
    }
    
    private String formatInstant(Instant instant) {
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
    
    // Getters
    public String getTokenId() { return tokenId; }
    public int getUserId() { return userId; }
    public Instant getRequestDate() { return requestDate; }
    public Instant getExpirationTime() { return expirationTime; }
}