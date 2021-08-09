package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a village card in the backend game world
 */
public class VillageCard extends Card {
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        super.type = type;
    }    
}
