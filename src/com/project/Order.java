package com.project;

import com.project.publication.Publication;

import java.io.Serializable;

public class Order implements Serializable {
    public static final long serialVersionUID = 1236L;
    private Publication publication;
    private int count;

    public Order(Publication publication, int count) {
        this.publication = publication;
        this.count = count;
    }

    public Publication getPublication() {
        return publication;
    }

    public float getExpenses(){
        return publication.getSingleManufacturingPrice() * count;
    }

    public float getIncome(){
        return publication.getClientPrice() * count;
    }

    @Override
    public String toString() {
        return "Order{" +
                "publication=" + publication +
                ", count=" + count +
                '}';
    }
}
