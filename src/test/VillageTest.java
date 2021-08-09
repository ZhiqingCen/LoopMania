package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Village;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import org.javatuples.Pair;

public class VillageTest {
    @Test    
    /** 
    * chenck if chcaracter health has arrised
    */        
    public void Test1(){
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(5,5);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP = new PathPosition(0, p1);
        PathPosition PP2 = new PathPosition(0, p2);

        Village B = new Village(x,y,"Village");
        Character C = new Character(PP);
        Character C2 = new Character(PP2);
        assertTrue(B.CharacterPositionCheck(C));
        assertFalse(B.CharacterPositionCheck(C2));
        C.addHealth(-20);
        B.CharacterHealthRecover(C);
        assertEquals(85, C.getHealth());   
  
        
    }
}
