package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class HealthPotion extends Equipment {
    private final static String name = "HealthPotion";
    private final static int purchaseValue = 80;
    private final static int sellValue = 40;
    private final static int healthAdd = 100;

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public int getPurchaseMoney() {
        return purchaseValue;
    }

    public int getSellMoney() {
        return sellValue;
    }

    public int getHealth() {
        return healthAdd;
    }

    /**
     * method refillHealth aims to add 100 health to character
     * @param character
     */
    public void refillHealth(Character character) {
        character.addHealth(healthAdd);
    }

}
