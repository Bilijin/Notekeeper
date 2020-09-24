package com.example.notekeeper;

public class Snippet {
    private int image;
    private String title;
    private String text;
    private String date;
    private long id;
    private NoteCategory category;

    public enum NoteCategory{PERSONAL,WORK,GENERAL,FOOD,HOBBIES}

    Snippet(int image, String title, String text,String date) {
        this.image = image;
        this.title= title;
        this.text = text;
        this.date = date;
    }

//    Snippet(String title, String text,String date, long id) {
//        this.title= title;
//        this.text = text;
//        this.date = date;
//        this.id = id;
//    }

    Snippet(String title, String text, String date, NoteCategory category, long id) {
        this.title= title;
        this.text = text;
        this.date = date;
        this.id = id;
        this.category = category;
    }

//    public int getImage() {
//        return image;
//    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public NoteCategory getCategory() {
        return category;
    }
}
