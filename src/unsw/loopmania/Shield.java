package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends Equipment {
    private final static String name = "Shield";
    private final static int purchaseValue = 60;
    private final static int sellValue = 30;
    private final static int defence = 15;

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
     * checkVampire method take enemy from method reduceAttackChance to check if the enemy is vampire
     * if it is vampire return ture, otherwise return false
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
     * method reduceAttackChance take vampire and check if it is vampire and reduce the
     * chance of critical attack to 40% 
     * @param enemy
     */
    public void reduceAttackChance(BasicEnemy enemy) {
        // enemy.setCriticalChance(0.4*enemy.getCriticalChance());
        if(checkVampire(enemy)) {
            double chance = ((Vampire)enemy).getCriticalChance();
            ((Vampire)enemy).setCriticalChance(0.4 * chance); 
        }
    }
}

