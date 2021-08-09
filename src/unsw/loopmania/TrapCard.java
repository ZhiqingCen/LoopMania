package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a Trap card in the backend game world
 */
public class TrapCard extends Card {
    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        super.type = type;
    }    
}
