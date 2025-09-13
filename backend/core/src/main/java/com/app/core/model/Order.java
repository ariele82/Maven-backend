package com.app.core.model;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private String orderNumber;
    private int customerId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;
    private int shippingAddressId;
    private String trackingNumber;
    
    // Costruttore vuoto
    public Order() {}
    
    // Costruttore con parametri
    public Order(String orderNumber, int customerId, double totalAmount, String status, int shippingAddressId) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddressId = shippingAddressId;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getShippingAddressId() { return shippingAddressId; }
    public void setShippingAddressId(int shippingAddressId) { this.shippingAddressId = shippingAddressId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
}
