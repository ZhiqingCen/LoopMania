package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends Equipment {
    private final static String name = "Stake";
    private final static int purchaseValue = 50;
    private final static int sellValue = 25;
    private final static int damage = 10;
    private final static int vampireDamage = 25;

    public Stake (SimpleIntegerProperty x, SimpleIntegerProperty y) {
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

    public int getVampireDamage() {
        return vampireDamage;
    }

    /**
     * add character normal damage when Stake equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.addDamage(damage);
    }

    /**
     * delete character damage Stake bring to when unequipped
     * @param character
     */
    public void unequipped(Character character) {
        character.addDamage(-damage);
    }

    /**
     * checkVampire method aims to check if the enemy is Vampire, if the enemy is vampire it will return ture
     * otherwise it will return false
     * @param enemy
     * @return
     */
    public boolean checkVampire(BasicEnemy enemy) {
        String name = enemy.getClass().getName();
        String[] names = name.split("\\.");
        String check = names[2];
        if(check.equals("Vampire")) {
            return true;
        }
        return false;
    }


    /**
     * add character vampireDamage when equipped
     * @param character
     */
    public void addFeatureVampire(Character character) {
        character.addDamage(vampireDamage); 
    }

    /**
     * reduce character damage to normal when unequipped
     * @param character
     */
    public void unequippedVampire(Character character) {
        character.addDamage(-vampireDamage); 
    }
    
}
