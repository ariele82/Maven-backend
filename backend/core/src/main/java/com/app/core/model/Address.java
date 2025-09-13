package com.app.core.model;

public class Address {
    private int id;
    private int customerId;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private boolean isDefault;
    
    // Costruttore vuoto
    public Address() {}
    
    // Costruttore con parametri
    public Address(int customerId, String street, String city, String postalCode, String country, boolean isDefault) {
        this.customerId = customerId;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.isDefault = isDefault;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
}
