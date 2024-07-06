package com.example.trackmate;
public class LuggageEntry {
    private String company;
    private String color;
    private int capacity;
    private double height;
    private double width;

    public LuggageEntry(String company, String color, int capacity, double height, double width) {
        this.company = company;
        this.color = color;
        this.capacity = capacity;
        this.height = height;
        this.width = width;
    }

    // Getters and setters for the attributes
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}

