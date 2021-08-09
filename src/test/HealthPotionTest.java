package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.HealthPotion;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class HealthPotionTest {
    
    /**
     * create character and healthpotion
     * test if the reduced health character get healthpotion
     * and have the get same health before reduce health
     */
    @Test
    public void refillHealthTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP = new PathPosition(0, p1);
    
        Character character = new Character(PP);
        HealthPotion healthPotion = new HealthPotion(x,y);

        int health = character.getHealth();
        assertDoesNotThrow(() -> character.addHealth(-100));
        assertDoesNotThrow(() -> healthPotion.refillHealth(character));
        int newHealth = character.getHealth();
        assertEquals(health, newHealth);
    }
}
