package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Anduril extends Equipment {
    private final static String name = "Anduril";
    private final static int damage = 15;

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    /**
     * check if the enemy is Doggie or ElanMuske which the item would effective for
     * @param enemy
     * @return
     */
    public boolean checkBoss(BasicEnemy enemy) {
        String type = enemy.getClass().getSimpleName();
        if (type.equals("Doggie") || type.equals("FinalBoss") ) {
            return true;
        }
        return false;
    }

    /**
     * add character damage to triple when equipped
     * @param character
     */
    public void addFeatureBoss(Character character) {
        character.addDamage(character.getDamage() * 2);
    }

    /**
     * reduce character damage to one of thired when unequipped
     * @param character
     */
    public void unequippedBoss(Character character) {
        character.addDamage(-((character.getDamage() / 3) * 2)); 
    }

    /**
     * add character normal damage when equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.addDamage(damage);
    }

    /**
     * delete character damage anduril bring to when unequipped
     * @param character
     */
    public void unequipped(Character character) {
        character.addDamage(-damage);
    }
}
