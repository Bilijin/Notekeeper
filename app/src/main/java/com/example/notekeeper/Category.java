package com.example.notekeeper;

public class Category {

    int image;
    String name;

    Category(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
