package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Vampire;
import unsw.loopmania.VampireCastleBuilding;
import org.javatuples.Pair;


/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class VampireCastleTest {
    @Test
    /**
     * chenck if Vampire has realeasrd
     */    
    public void Test1(){
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);

        VampireCastleBuilding B = new VampireCastleBuilding(x,y,"ZombiePit");
        Vampire V = B.releaseVampire();
        assertEquals(20, V.getDamage());
    }
    

}
