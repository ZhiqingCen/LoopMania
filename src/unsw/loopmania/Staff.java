package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;

public class Staff extends Equipment {
    private final static String name = "Staff";
    private final static int purchaseValue = 40;
    private final static int sellValue = 20;
    private final static int damage = 5;
    private Random random;

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        random = new Random();
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

    public void setRandom(int seed) {
        random = new Random(seed);
    }

    /**
     * add character normal damage when Staff equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.addDamage(damage);
    }

    /**
     * delete character damage Staff bring to when unequipped
     * @param character
     */
    public void unequipped(Character character) {
        character.addDamage(-damage);
    }

    /**
     * changeEnemy method make enemy health reduced to 0 in a spicific chance
     * @param enemy
     */
    public void changeEnemy(BasicEnemy enemy) {
        // Random rand = new Random();
        int rand_int = random.nextInt(10);
        if (rand_int > 5) {
            enemy.addHealth(-100);
        }
    }

}
