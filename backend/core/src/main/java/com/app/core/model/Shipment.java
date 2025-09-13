package com.app.core.model;

import java.time.LocalDateTime;

public class Shipment {
    private int id;
    private int orderId;
    private LocalDateTime shippedAt;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime actualDelivery;
    private String carrier;
    private String status;
    
    // Costruttore vuoto
    public Shipment() {}
    
    // Costruttore con parametri
    public Shipment(int orderId, String carrier, String status) {
        this.orderId = orderId;
        this.carrier = carrier;
        this.status = status;
        this.shippedAt = LocalDateTime.now();
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public LocalDateTime getShippedAt() { return shippedAt; }
    public void setShippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; }
    public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    public LocalDateTime getActualDelivery() { return actualDelivery; }
    public void setActualDelivery(LocalDateTime actualDelivery) { this.actualDelivery = actualDelivery; }
    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
