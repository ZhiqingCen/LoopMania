package test;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.Item;
import unsw.loopmania.Gold;
public class GoldTest {
    @Test
    public void addGoldTest() {
        Item item = new Item();
        Gold gold = new Gold(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        gold.addGold(item, 10);
        assertEquals(10, item.getGold());
    }
}
