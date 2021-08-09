package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a Tower card in the backend game world
 */
public class TowerCard extends Card {
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        super.type = type;
    }    
}