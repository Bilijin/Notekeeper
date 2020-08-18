package com.example.notekeeper;

public class Snippet {
    private final int image;
    private final String title;
    private final String text;
    private final int date;

    Snippet(int image, String title, String text,int date) {
        this.image = image;
        this.title= title;
        this.text = text;
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getDate() {
        return date;
    }
}
