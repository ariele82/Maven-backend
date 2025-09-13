package com.app.core.repository;

import com.app.core.config.DatabaseConfig;
import com.app.core.model.User;
import com.app.core.security.EncryptionUtil;
import java.sql.*;

public class UserRepository {
    public User authenticate(String username, String password) {
        String sql = "SELECT id, username, rsa_public_key FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    "" // Password vuota, verrà verificata dopo
                );
                user.setId(rs.getInt("id"));
                user.setEncryptedPassword(rs.getString("rsa_public_key"));
                
                if (user.checkPassword(password)) {
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public String registerUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, rsa_public_key, ruolo) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            String encryptedPassword = EncryptionUtil.encrypt(password);
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            stmt.setString(3, role);
            
            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                return "User " + username + " registrato correttamente!";
            } else {
                return "Registrazione fallita per " + username;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore SQL: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore: " + e.getMessage();
        }
    }
        // Verifica se l'username esiste già
    public boolean usernameExists(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next(); // Restituisce true se trova almeno un record
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Verifica se l'email esiste già
    public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next(); // Restituisce true se trova almeno un record
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Elimina un utente per username
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            int rowsDeleted = stmt.executeUpdate();
            
            return rowsDeleted > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Metodo registerUser esistente (modificato per gestire più campi)
    public String registerUser(String username, String password, String email, String phone, String role) {
        String sql = "INSERT INTO users (username, rsa_public_key, email, phone, ruolo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Cripta la password
            String encryptedPassword = EncryptionUtil.encrypt(password);
            
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword); // Usiamo rsa_public_key per la password criptata
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, role);
            
            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                // Recupera l'id generato dal DB
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                int userId = -1;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
                
               
                return "User " + username + " registrato correttamente!";
            } else {
                return "Registrazione fallita per " + username;
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione: " + e.getMessage());
            return "Errore SQL: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("Errore durante la registrazione: " + e.getMessage());
            return "Errore: " + e.getMessage();
        }
    }
}
