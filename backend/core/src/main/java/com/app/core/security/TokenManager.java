package com.app.core.security;

import com.app.core.model.Token;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManager {
    private static final Map<String, Token> activeTokens = new ConcurrentHashMap<>();
    
    public static Token createToken(int userId) {
        Token token = new Token(userId);
        activeTokens.put(token.getTokenId(), token);
        return token;
    }
    
    public static Token validateToken(String tokenId) {
        Token token = activeTokens.get(tokenId);
        if (token != null && token.isValid()) {
            token.extend();
            return token;
        }
        return null;
    }
    
    public static void revokeToken(String tokenId) {
        activeTokens.remove(tokenId);
    }
    
    public static Map<String, Token> getUserTokens(int userId) {
        Map<String, Token> userTokens = new ConcurrentHashMap<>();
        for (Map.Entry<String, Token> entry : activeTokens.entrySet()) {
            if (entry.getValue().getUserId() == userId) {
                userTokens.put(entry.getKey(), entry.getValue());
            }
        }
        return userTokens;
    }
    
    public static void revokeUserTokens(int userId) {
        activeTokens.entrySet().removeIf(entry -> entry.getValue().getUserId() == userId);
    }
    
    public static String getActiveTokensInfo() {
        StringBuilder sb = new StringBuilder("Active Tokens:\n");
        for (Token token : activeTokens.values()) {
            sb.append(token.getTokenInfo()).append("\n");
        }
        return sb.toString();
    }
}