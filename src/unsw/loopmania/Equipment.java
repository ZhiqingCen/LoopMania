package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Equipment extends StaticEntity {
    private String name;

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName(){
        return this.name;
    }
}
