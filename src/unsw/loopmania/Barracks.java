package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;
/**
 * a basic form of building in the world
 */
public class Barracks extends Building{
    

    public Barracks(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        if(type == "Barracks")super.type = "Barracks";
    }

    /**
     * chenck if character position is equal to Barracks
     * @param C
     * @return boolean
     */
    public boolean CharacterPositionCheck(Character C){
        Pair<Integer,Integer> pos = C.getPosition();
        if(pos.getValue0() == this.getX() && pos.getValue1() == this.getY())return true;
        return false;
    }
    /**
     * realease a Solider allied with character
     * @param C
     * @return Soldier
     */
    public boolean SoliderRealease(Character C){
        return C.addAlly();
    }

}
