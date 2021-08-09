package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import java.util.*;
import org.javatuples.Pair;

/**
 * a basic form of building in the world
 */
public class ZombiePit extends Building {
    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y, String Type) {
        super(x, y);
        if(Type == "ZombiePit")super.type = "ZombiePit";
    }

    /**
     * realease a Zombie from Zopmbie Pit
     * @return Zombie
     */
    public Zombie releaseZombie(){
        List<Pair<Integer,Integer>> p = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(this.getX(),this.getY());
        p.add(pos1);
        PathPosition P = new PathPosition(0,p);
        Zombie Z = new Zombie(P);
        return Z;
    }


}
