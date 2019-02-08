package com.company;

public enum Coin {
    NICKEL(5),
    DIME(10),
    QUARTER(25),
    DOLLAR(100);

    public int value;

    Coin(int value) {
       this.value = value;
    }

}
