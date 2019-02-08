package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.Coin.DIME;
import static com.company.Coin.NICKEL;
import static com.company.Coin.QUARTER;

public class ChangeCalculator {

    public static List<Coin> calculateChange(InsertedMoney insertedMoney, Item item) {
        double changeSum = insertedMoney.getSum() - item.getPrice();

        List<Coin> change = new ArrayList<>();

        List<Coin> coinTypes = Arrays.asList(Coin.DOLLAR, QUARTER, DIME, NICKEL);

        for (Coin coinType : coinTypes) {
            while (changeSum >= coinType.value) {
                change.add(coinType);
                changeSum -= coinType.value;
            }
        }

        return change;
    }
}
