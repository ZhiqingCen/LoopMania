package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Equipment {
    
    private final static String name = "Helmet";
    private final static int purchaseValue = 50;
    private final static int sellValue = 25;
    private final static int defence = 15;
    private final static int reducedDamage = 5;
    private final static int reducedAttack = 5;

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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

    public int getDefence() {
        return defence;
    }

    public int getReducedAttack() {
        return reducedAttack;
    }

    /**
     * method reducedDamage aim to reduce 5 damage of enemy's damage 
     * @param enemy
     */
    public void reducedDamage(BasicEnemy enemy) {
            enemy.setDamage(enemy.getDamage() - reducedDamage);
    }
    
    /**
     * add character attack when equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.addDamage(-reducedAttack);
        character.setDefence(character.getDefence() + defence);
    }

    /**
     * delete character attack when unequipped
     * @param character
     */
    public void unequipped(Character character) {
        character.addDamage(reducedAttack);
        character.setDefence(character.getDefence() - defence);
    }
}
