package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends Equipment {
    
    private final static String name = "Armour";
    private final static int purchaseValue = 60;
    private final static int sellValue = 30;
    private final static int defence = 15;

    public Armour (SimpleIntegerProperty x, SimpleIntegerProperty y) {
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

    public int getdefence() {
        return defence;
    }

    /**
     * add character normal defence when equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.setDefence(character.getDefence() + defence);
    }

    /**
     * delete character normal defence when unequipped
     * @param character
     */
    public void unequipped(Character character) {
        character.setDefence(character.getDefence() - defence);
    }

    /**
     * half enemy attack
     * @param enemy
     */
    public void halfAttack(BasicEnemy enemy) {
        int newDamage = enemy.getDamage() / 2;
        enemy.setDamage(newDamage);
    }
}
