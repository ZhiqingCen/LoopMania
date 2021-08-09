package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Equipment {

    private final static String name = "Sword";
    private final static int purchaseValue = 20;
    private final static int sellValue = 10;
    private final static int damage = 15;

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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

    public int getDamage() {
        return damage;
    }

    /**
     * add character normal damage when Sword equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.addDamage(damage);
    }

    /**
     * delete character damage Sword bring to when unequipped
     * @param character
     */
    public void unequipped(Character character) {
        character.addDamage(-damage);
    }
}

