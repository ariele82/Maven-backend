package com.app.core.model;

import com.app.core.security.EncryptionUtil;

public class User {
    private int id;
    private String username;
    private String encryptedPassword;
    
    public User(String username, String password) {
        this.username = username;
        this.encryptedPassword = encryptPassword(password);
    }
    
    private String encryptPassword(String password) {
        try {
            return EncryptionUtil.encrypt(password);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }
    
    public boolean checkPassword(String plainPassword) {
        try {
            String decrypted = EncryptionUtil.decrypt(encryptedPassword);
            return decrypted.equals(plainPassword);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String encryptedPassword) { 
        this.encryptedPassword = encryptedPassword; 
    }
}
