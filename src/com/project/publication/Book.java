package com.project.publication;

public class Book extends Publication{
    public Book(String n, int pCount) {
        super(n, pCount, PaperType.Default.toString(), PaperSize.A5.toString());
    }
}
