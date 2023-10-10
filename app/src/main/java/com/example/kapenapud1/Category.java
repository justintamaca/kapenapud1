package com.example.kapenapud1;

public class Category {
    private int id;
    private String name;
    private int imageResourceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
