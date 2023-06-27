package com.project.publication;

public class Poster extends Publication{
    public Poster(String n) {
        super(n, 1, PaperType.Glossy.toString(), PaperSize.A1.toString());
    }
}
