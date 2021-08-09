package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Gold extends Equipment {
    private final static String name = "Gold";
    private int amount;

    public Gold(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return amount;
    }
    public void setGold(int amount) {
        this.amount = amount;
    }

    /**
     * add gold to item
     * @param item
     * @param amount
     */
    public void addGold(Item item, int amount) {
        item.setGold(amount+ item.getGold());
    }
}
