package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a Barracks card in the backend game world
 */
public class BarracksCard extends Card {
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        super.type = type;
    }    
}