package com.app.core.repository;

import com.app.core.config.DatabaseConfig;
import com.app.core.model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, order_number, customer_id, order_date, total_amount, status, shipping_address_id, tracking_number FROM orders";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddressId(rs.getInt("shipping_address_id"));
                order.setTrackingNumber(rs.getString("tracking_number"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    public Order getOrderById(int id) {
        String sql = "SELECT id, order_number, customer_id, order_date, total_amount, status, shipping_address_id, tracking_number FROM orders WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddressId(rs.getInt("shipping_address_id"));
                order.setTrackingNumber(rs.getString("tracking_number"));
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Order> getOrdersByCustomer(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, order_number, customer_id, order_date, total_amount, status, shipping_address_id, tracking_number FROM orders WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setOrderNumber(rs.getString("order_number"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddressId(rs.getInt("shipping_address_id"));
                order.setTrackingNumber(rs.getString("tracking_number"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    public int createOrder(Order order) {
        String sql = "INSERT INTO orders (order_number, customer_id, total_amount, status, shipping_address_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, order.getOrderNumber());
            stmt.setInt(2, order.getCustomerId());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getShippingAddressId());
            
            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET order_number = ?, customer_id = ?, total_amount = ?, status = ?, shipping_address_id = ?, tracking_number = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, order.getOrderNumber());
            stmt.setInt(2, order.getCustomerId());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getShippingAddressId());
            stmt.setString(6, order.getTrackingNumber());
            stmt.setInt(7, order.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}