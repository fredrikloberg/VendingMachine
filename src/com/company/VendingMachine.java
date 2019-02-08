package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.company.Coin.DIME;
import static com.company.Coin.NICKEL;
import static com.company.Coin.QUARTER;

public class VendingMachine {

    public static final String SOLD_OUT = "Sold out";
    public static final String NOT_ENOUGH_MONEY = "Not enough money";
    public static final String INVALID_COMMAND = "Invalid command";

    static class Model {
        public final List<Coin> insertedCoins;
        public final List<Item> items;
        public final String output;

        public Model(List<Coin> insertedCoins, List<Item> items, String output) {
            this.insertedCoins = Collections.unmodifiableList(insertedCoins);
            this.items = Collections.unmodifiableList(items);
            this.output = output;
        }

        public static Model withNewCoin(final Model model, final Coin coin) {
            final List<Coin> newList = new ArrayList<>(model.insertedCoins);
            newList.add(coin);

            return new Model(newList, model.items, model.output);
        }

        public static Model withNoCoins(final Model model) {
            return new Model(Collections.emptyList(), model.items, model.output);
        }

        public static Model withOutput(final Model model, final String output) {
            return new Model(model.insertedCoins, model.items, output);
        }

        public static Model withRemovedItem(final Model model, final String itemId) {
            Item itemForRemoval = model.items.stream()
                    .filter(i -> i.id.equals(itemId))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            List<Item> newList = model.items.stream()
                    .filter(i -> i != itemForRemoval)
                    .collect(Collectors.toList());

            return new Model(model.insertedCoins, newList, model.output);
        }
    }

    public static void main(String[] args) {

        Model model = new Model(Collections.emptyList(), Arrays.asList(), "");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter input: ");
            String input = scanner.nextLine();

            model = update(model, input);

            System.out.println(model.output);

        }
    }

    public static Model update(final Model model, final String input) {
        String[] commands = input.toUpperCase().split("-");
        switch (commands[0]) {
            case "N":
                return Model.withNewCoin(model, Coin.NICKEL);
            case "D":
                return Model.withNewCoin(model, Coin.DIME);
            case "Q":
                return Model.withNewCoin(model, QUARTER);
            case "DOLLAR":
                return Model.withNewCoin(model, Coin.DOLLAR);
            case "COIN RETURN":
                final String returnedCoins = getCoinsString(model.insertedCoins);
                final Model modelWithoutCoins = Model.withNoCoins(model);
                return Model.withOutput(modelWithoutCoins, returnedCoins);
            case "GET":
                if (commands.length > 1) {
                    return processGetItem(model, commands[1]);
                }
            default:
                return Model.withOutput(model, INVALID_COMMAND);
        }
    }

    public static Model processGetItem(Model model, String itemId) {
        Optional<Item> item = model.items.stream().filter(i -> i.id.equals(itemId)).findFirst();

        if (!item.isPresent()) {
            return Model.withOutput(model, SOLD_OUT);
        }

        if (item.get().price > getSum(model.insertedCoins)) {
            return Model.withOutput(model, NOT_ENOUGH_MONEY);
        } else {
            List<Coin> change = calculateChange(getSum(model.insertedCoins), item.get());

            Model withRemovedItem = Model.withRemovedItem(model, itemId);
            Model withNoInsertedCoins = Model.withNoCoins(withRemovedItem);
            return Model.withOutput(withNoInsertedCoins, item.get().id + ", " + getCoinsString(change));
        }
    }

    public static String getCoinsString(List<Coin> coins) {
        return coins.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(", "));
    }

    public static int getSum(List<Coin> coins) {
        return coins.stream().map(c -> c.value).reduce((a, i) -> a + i).orElse(0);
    }

    public static List<Coin> calculateChange(double sum, Item item) {
        double changeSum = sum - item.price;

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
