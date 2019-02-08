package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stock {
    private List<Item> items = new ArrayList<>();

    public void addDefaultItems() {
        items.add(new Cola());
    }

    public Optional<Item> getItem(String itemId) {
        return items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();
    }

    public void removeItem(String itemId) {
        Item itemForRemoval = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        items.remove(itemForRemoval);
    }
}
