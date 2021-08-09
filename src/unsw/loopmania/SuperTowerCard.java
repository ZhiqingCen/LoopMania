package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class SuperTowerCard extends Card{
    public SuperTowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        if(type == "SuperTowerCard") super.type = type;
        
    }
}
