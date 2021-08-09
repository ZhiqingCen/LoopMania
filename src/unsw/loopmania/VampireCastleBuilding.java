package unsw.loopmania;


import org.javatuples.Pair;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.*;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building{
    public int cycle;
    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y, String Type) {
        super(x, y);
        if(Type == "VampireCastle")super.type = "VampireCastle";
    }
    
    /**
     * realease Vampire unit
     * @return Vampire
     */
    public Vampire releaseVampire(){
        List<Pair<Integer,Integer>> p = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(this.getX(),this.getY());
        p.add(pos1);
        PathPosition P = new PathPosition(0,p);
        Vampire V = new Vampire(P);
        return V;
    }

}
