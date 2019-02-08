package com.company;

public class Cola implements Item {

    @Override
    public String getId() {
        return "COLA";
    }

    @Override
    public double getPrice() {
        return 1.5;
    }
}
