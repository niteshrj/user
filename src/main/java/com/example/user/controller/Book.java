package com.example.user.controller;

public class Book {

    private String id;
    private String name;
    private int price;

    public Book() {
    }

    public Book(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
