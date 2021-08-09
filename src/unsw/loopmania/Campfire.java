package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;

/**
 * a basic form of building in the world
 */
public class Campfire extends Building {
    public final int attackdouble = 2;
    public final int effectiveRadius = 1;
    

    public Campfire(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        if(type == "Campfire")super.type = "Campfire";
    }

    /**
     * chenck if character position is in the effective radius of Campfire
     * @param C
     * @return boolean
     */
    public boolean CharacterPositionCheck(Character C){
        Pair<Integer,Integer> p = C.getPosition();
        if(Math.abs(this.getX() - p.getValue0())<= effectiveRadius && Math.abs(this.getY() - p.getValue1())<= effectiveRadius)return true;
        return false;
    }
    
    /**
     * double the attack for chcaracter
     * @param C
     */
    public void doubleAttack(Character C){
        if(CharacterPositionCheck(C))C.addDamage(C.getDamage());
    }
    /**
     * recover the health for character
     * @param C
     */
    public void restoreAttack(Character C){
        if(!CharacterPositionCheck(C))C.addDamage(-C.getDamage()/2);
    }
}
