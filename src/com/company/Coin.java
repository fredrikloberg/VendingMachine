package com.company;

public enum Coin {
    NICKEL(0.05),
    DIME(0.10),
    QUARTER(0.25),
    DOLLAR(1.00);

    public double value;

    Coin(double value) {
       this.value = value;
    }

}
