package com.example.luxevista;

public class Testimonial {
    private int imageResource;
    private String text;

    public Testimonial(int imageResource, String text) {
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }
}

