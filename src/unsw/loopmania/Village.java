package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;
/**
 * a basic form of building in the world
 */
public class Village extends Building {
    public final int recovery = 5;

    public Village(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        if(type == "Village")super.type = "Village";
    }

    /**
     * chenck if character position is equal to villiage's
     * @param C
     * @return boolean
     */
    public boolean CharacterPositionCheck(Character C){
        Pair<Integer,Integer> pos = C.getPosition();
        if(pos.getValue0() == this.getX() && pos.getValue1() == this.getY())return true;
        return false;
    }
    /**
     * add health to character
     * @param C
     */
    public void CharacterHealthRecover(Character C){
        C.addHealth(recovery);
    }
    

}