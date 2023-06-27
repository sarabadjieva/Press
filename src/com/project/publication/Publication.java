package com.project.publication;

import java.io.Serializable;

public class Publication implements Serializable {
    private final String name;
    private final int pagesCount;
    private final PaperType paperType;
    private final PaperSize pageSize;
    private float clientPrice;

    public Publication(String n, int pCount, String pType, String pSize){
        name = n;
        pagesCount = pCount;
        paperType = PaperType.fromString(pType);
        pageSize = PaperSize.fromString(pSize);
    }

    public String getName() { return name; }

    public int getPagesCount() { return pagesCount; }

    public float getClientPrice(){ return clientPrice; }

    public void setClientPrice(float cPrice){ clientPrice = cPrice; }

    public float getSingleManufacturingPrice(){
        float pagePrice = 0;
        switch (paperType){

            case Default -> pagePrice += .1;
            case Glossy -> pagePrice += .2;
            case Newspaper -> pagePrice += .3;
        }
        switch (pageSize){

            case A1 -> pagePrice += .5;
            case A2 -> pagePrice += .4;
            case A3 -> pagePrice += .3;
            case A4 -> pagePrice += .2;
            case A5 -> pagePrice += .1;
        }

        return pagesCount * pagePrice;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "name='" + name + '\'' +
                ", pagesCount=" + pagesCount +
                ", paperType=" + paperType +
                ", pageSize=" + pageSize +
                ", clientPrice=" + clientPrice +
                ", manufacturingPrice=" + getSingleManufacturingPrice() +
                '}';
    }
}
