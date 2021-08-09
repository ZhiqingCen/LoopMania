package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a ZombiePit card in the backend game world
 */
public class ZombiePitCard extends Card {
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        super.type = type;
    }    
}
