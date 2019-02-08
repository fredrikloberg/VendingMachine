package com.company;

import com.company.VendingMachine.Model;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class VendingMachineTest {

    @Test
    public void testWithNoStock() {
        Model model = new Model(Collections.emptyList(), Arrays.asList(), "");

        Model newModel = VendingMachine.update(model, "GET-cola");

        assertEquals(VendingMachine.SOLD_OUT, newModel.output);
    }

    @Test
    public void testWithoutEnoughMoney() {
        Model model = new Model(Collections.emptyList(), Arrays.asList(Item.COLA, Item.SOLO), "");

        Model newModel = VendingMachine.update(model, "GET-cola");

        assertEquals(VendingMachine.NOT_ENOUGH_MONEY, newModel.output);
    }

    @Test
    public void testBuyCola() {
        Model model = new Model(Collections.emptyList(), Arrays.asList(Item.COLA, Item.SOLO), "");

        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "Q");
        model = VendingMachine.update(model, "GET-COLA");

        assertEquals(Item.COLA.id + ", " + Coin.QUARTER.toString(), model.output);
    }

}