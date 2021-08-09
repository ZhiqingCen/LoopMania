package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TheOneRing extends Equipment {
    private final static String name = "TheOneRing";

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    /**
     * makeLive adds max health to character
     * @param character
     */
    public void makeLive(Character character) {
        character.setLives(character.getLives()+1);
    }

    /**
     * unequipped make live mins one
     * @param character
     */
    public void unequipped(Character character) {
        character.setLives(character.getLives()-1);
    }

}

