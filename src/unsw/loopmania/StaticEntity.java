package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;


/**
 * represents a non-moving entity
 * unlike the moving entities, this can be placed anywhere on the game map
 */
public abstract class StaticEntity extends Entity {
    /**
     * x and y coordinates represented by IntegerProperty, so ChangeListeners can be added
     */
    private IntegerProperty x, y;

    public StaticEntity(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super();
        this.x = x;
        this.y = y;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }
    public Pair<Integer,Integer> getPosition() {
        Pair<Integer,Integer> location = new Pair<>(getX(), getY());
        return location;
    }
}
