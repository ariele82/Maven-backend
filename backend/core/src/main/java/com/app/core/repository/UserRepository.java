package com.app.core.repository;

import com.app.core.config.DatabaseConfig;
import com.app.core.model.User;
import com.app.core.security.EncryptionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public User authenticate(String username, String password) {
       String sql = "SELECT id, username, password, email, phone, role, is_active FROM users WHERE username = ? AND is_active = true";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                 User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                user.setEncryptedPassword(rs.getString("password"));
                
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
        String sql = "INSERT INTO users (username, password, ruolo) VALUES (?, ?, ?)";
        
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
            
            return rs.next();
            
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
            
            return rs.next();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Registra nuovo utente
    public String registerUser(String username, String password, String email, String phone, String role) {
        String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Cripta la password
            String encryptedPassword = EncryptionUtil.encrypt(password);
            
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, role);
            
            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
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
    
    // Elimina utente (soft delete)
    public boolean deleteUser(String username) {
        String sql = "UPDATE users SET is_active = false WHERE username = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            int rowsUpdated = stmt.executeUpdate();
            
            return rowsUpdated > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Elimina utente definitivamente
    public boolean hardDeleteUser(String username) {
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
    
    // Ottieni utente per ID
    public User getUserById(int id) {
        String sql = "SELECT id, username, password, email, phone, role, is_active FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEncryptedPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Ottieni tutti gli utenti
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, email, phone, role, is_active FROM users ORDER BY username";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEncryptedPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    // Aggiorna ruolo utente
    public boolean updateUserRole(int userId, String newRole) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newRole);
            stmt.setInt(2, userId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Attiva/disattiva utente
    public boolean setUserStatus(int userId, boolean isActive) {
        String sql = "UPDATE users SET is_active = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, isActive);
            stmt.setInt(2, userId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}