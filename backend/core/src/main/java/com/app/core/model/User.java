package com.app.core.model;

import com.app.core.security.EncryptionUtil;

public class User {
    private int id;
    private String username;
    private String password; // memorizzata sempre cifrata
    private String email;
    private String phone;
    private String role;
    private boolean isActive;

    // Costruttore vuoto
    public User() {}

    // Costruttore per registrazione
    public User(String username, String password, String email, String phone, String role) {
        this.username = username;
        setPassword(password); // uso il setter così cifra subito
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.isActive = true;
    }

    // Costruttore per login
    public User(String username, String password) {
        this.username = username;
        setPassword(password); // cifra anche qui
    }

    // Cifratura
    private String encryptPassword(String password) {
        try {
            return EncryptionUtil.encrypt(password);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    // Decifratura
    private String decryptPassword(String encryptedPassword) {
        try {
            return EncryptionUtil.decrypt(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }

    // Confronto password in chiaro con quella salvata
    public boolean checkPassword(String plainPassword) {
        try {
            String decrypted = decryptPassword(this.password);
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

    // ⚠️ restituisce la password in chiaro (decifrata)
    public String getPassword() { 
        return decryptPassword(this.password); 
    }

    // ⚠️ cifra sempre la password prima di salvarla
    public void setPassword(String password) { 
        this.password = encryptPassword(password); 
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // Getter/Setter per password già cifrata (se arriva da DB)
    public String getEncryptedPassword() { return password; }
    public void setEncryptedPassword(String encryptedPassword) { 
        this.password = encryptedPassword; 
    }
}

