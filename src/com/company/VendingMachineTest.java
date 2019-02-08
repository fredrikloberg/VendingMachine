package com.company;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VendingMachineTest {

    @Test
    public void testWithoutEnoughMoney() {
        InsertedMoney insertedMoney = new InsertedMoney();

        List<Coin> coins = ChangeCalculator.calculateChange(insertedMoney, new Cola());

        assertEquals(0, getSum(coins), 0.000001);
    }

    @Test
    public void testWithTooMuchMoney() {
        InsertedMoney insertedMoney = new InsertedMoney();
        insertedMoney.add(Coin.DOLLAR);
        insertedMoney.add(Coin.DOLLAR);

        List<Coin> coins = ChangeCalculator.calculateChange(insertedMoney, new Cola());

        assertEquals(2, coins.size());
        assertEquals(Coin.QUARTER, coins.get(0));
        assertEquals(Coin.QUARTER, coins.get(1));
    }


    public double getSum(List<Coin> coins) {
        return coins.stream().map(c-> c.value).reduce((a, i) -> a + i).orElse(0.0);
    }

}