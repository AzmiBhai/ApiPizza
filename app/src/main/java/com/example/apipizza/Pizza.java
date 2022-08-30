package com.example.apipizza;

import java.io.Serializable;

public class Pizza implements Serializable {
    private String name, imageUrl,description;
    private int id,stock;
    private double price;

    public Pizza(String name, String imageUrl, String description, int id, double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.id = id;
        this.price = price;
    }

    public Pizza() {
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}
