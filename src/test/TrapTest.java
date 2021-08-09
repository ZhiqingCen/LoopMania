package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Trap;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Vampire;
import org.javatuples.Pair;

public class TrapTest {
    @Test
    /**
     * chenck if enemy health is reduced
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
        PathPosition PP = new PathPosition(0, p1);

        Trap B = new Trap(x,y,"Trap");
        Vampire V = new Vampire(PP);
        V.setDetails(100, 100, 5, 5, 5, 5);
        B.enemiesHPReduce(V);
        //Enemy
        assertTrue(B.EnemeyPositionCheck(V));
        assertTrue(V.getHealth() == 95);

        
        
    }
}
