package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Tower;
import unsw.loopmania.Vampire;
import unsw.loopmania.Character;
import org.javatuples.Pair;

public class TowerTest {
    @Test
    /**
     * chenck if enemy health is reduced
     */    
    public void Test1(){
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p3 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        Pair<Integer,Integer> pos3 = new Pair<>(5,5);
        p1.add(pos1);
        p2.add(pos2);
        p3.add(pos3);
        PathPosition PP = new PathPosition(0, p1);
        PathPosition PP1 = new PathPosition(0, p2);
        PathPosition PP2 = new PathPosition(0, p3);

        Tower B = new Tower(x,y,"Tower");
        Character C = new Character(PP);
        Vampire V = new Vampire(PP1);
        Character C2 = new Character(PP2);
        Vampire V1 = new Vampire(PP2);

        //Enemy
        assertFalse(B.CharacterPositionCheck(C2));
        assertFalse(B.EnemeyPositionCheck(V1));
        assertTrue(B.CharacterPositionCheck(C));
        assertTrue(B.EnemeyPositionCheck(V));
        B.enemiesHPReduce(V);
        assertEquals(45, V.getHealth());


    }
}
