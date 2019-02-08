package com.company;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.company.ChangeCalculator.calculateChange;
import static com.company.Coin.QUARTER;

public class VendingMachine {

    private InsertedMoney insertedMoney = new InsertedMoney();
    private Stock stock = new Stock();

    public static void main(String[] args) {
        new VendingMachine().commandLine();
    }

    public VendingMachine() {
        stock.addDefaultItems();
    }

    private void commandLine() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter input: ");
            String input = scanner.nextLine().toUpperCase();

            interpretCommand(input);


        }
    }

    private void interpretCommand(String input) {
        switch (input) {
            case "N":
                insertedMoney.add(Coin.NICKEL);
                break;
            case "D":
                insertedMoney.add(Coin.DIME);
                break;
            case "Q":
                insertedMoney.add(QUARTER);
                break;
            case "DOLLAR":
                insertedMoney.add(Coin.DOLLAR);
                break;
            case "COIN RETURN":
                String returnedCoins = getCoinsString(insertedMoney.getCoins());
                insertedMoney.eject();
                System.out.println(returnedCoins);
                break;

            default:
                if (isGetItemCommand(input)) {
                    String itemId = input.split("-")[1];
                    String output = processGetItem(stock, itemId);
                    System.out.println(output);
                }
        }
    }

    private boolean isGetItemCommand(String input) {
        return input.startsWith("GET-") && input.length() > 4;
    }

    private String processGetItem(Stock stock, String itemId) {
        Optional<Item> item = stock.getItem(itemId);

        if (!item.isPresent()) {
            return "Sold out";
        }

        if (item.get().getPrice() > insertedMoney.getSum()) {
            return "Not enough money";
        } else {
            List<Coin> change = calculateChange(insertedMoney, item.get());
            stock.removeItem(item.get().getId());
            insertedMoney.eject();

            return item.get().getId() + ", " + getCoinsString(change);
        }
    }

    public static String getCoinsString(List<Coin> coins) {
        return coins.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(", "));
    }
}
