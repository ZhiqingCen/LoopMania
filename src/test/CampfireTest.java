package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.Campfire;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import org.javatuples.Pair;

public class CampfireTest {
    
    @Test
    /**
     * test if the Damage of character has changed
     */
    public void Test1(){
        SimpleIntegerProperty x = new SimpleIntegerProperty(1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(1);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(5,5);
        Pair<Integer,Integer> pos3 = new Pair<>(1,1);
        p2.add(pos2);
        p1.add(pos1);

        PathPosition PP = new PathPosition(0, p1);
        PathPosition PP1 = new PathPosition(0, p2);

        Campfire B = new Campfire(x,y,"Campfire");
        Character C = new Character(PP);
        Character C2 = new Character(PP1);
        assertTrue(B.CharacterPositionCheck(C));
        assertFalse(B.CharacterPositionCheck(C2));
        assertEquals(pos3, B.getPosition());
        B.doubleAttack(C);
        B.restoreAttack(C2);
        assertEquals(20, C.getDamage());
        assertEquals(5, C2.getDamage());

    }
}
