package com.company;

import java.util.ArrayList;
import java.util.List;

public class InsertedMoney {

    private List<Coin> coins = new ArrayList<>();

    public void add(Coin c) {
        coins.add(c);
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void eject() {
        coins.clear();
    }

    public double getSum() {
        return coins.stream().map(c-> c.value).reduce((a, i) -> a + i).orElse(0.0);
    }
}
