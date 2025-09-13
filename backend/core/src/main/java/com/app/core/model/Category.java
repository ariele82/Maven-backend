package com.app.core.model;

public class Category {
    private int id;
    private String name;
    private String description;
    
    // Costruttore vuoto
    public Category() {}
    
    // Costruttore con parametri
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
