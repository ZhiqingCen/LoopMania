package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TreeStump extends Equipment {

    private final static String name = "TreeStump";
    private final static int defence = 15;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public int getDefence() {
        return defence;
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
     * add character defence to double when equipped
     * @param character
     */
    public void addFeatureBoss(Character character) {
        character.setDefence(character.getDefence() * 2);
    }

    /**
     * back character damage from double when equipped
     * @param character
     */
    public void unequippedBoss(Character character) {
        character.setDefence(character.getDefence() / 2); 
    }

    /**
     * add character normal defence when equipped
     * @param character
     */
    public void addFeature(Character character) {
        character.setDefence(character.getDefence() + defence);
    }

    /**
     * back character defence when equipped
     * @param character
     */
    public void unequipped(Character character) {
        character.setDefence(character.getDefence() - defence);
    }
}
