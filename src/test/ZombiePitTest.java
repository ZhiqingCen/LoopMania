package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.ZombiePit;
import unsw.loopmania.Zombie;
import org.javatuples.Pair;

public class ZombiePitTest {
    @Test
    /**
     * chenck if Zombie is realeased health is reduced
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

        ZombiePit B = new ZombiePit(x,y,"ZombiePit");
        Zombie Z = B.releaseZombie();
        //Enemy
        assertEquals(pos1, Z.getPosition());

        
        
    }
}
