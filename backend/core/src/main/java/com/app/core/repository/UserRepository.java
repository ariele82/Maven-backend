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
}
