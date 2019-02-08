package com.company;

public enum Item {
    COLA("COLA", 150),
    SOLO("SOLO", 350);

    public final String id;
    public final int price;

    Item(String id, int price) {
        this.id = id;
        this.price = price;
    }
}
