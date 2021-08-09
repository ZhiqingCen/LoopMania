package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;
import java.lang.Math;
import unsw.loopmania.Vampire;
import unsw.loopmania.Slug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.Character;
import unsw.loopmania.Shield;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class ShieldTest {

    /**
     * create vampire and shield to check
     * if the campire's critical attack chance change to 
     * 40% compare to original chance 
     */
    @Test
    public void reduceAttackChanceTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p2.add(pos2);
        PathPosition PP1 = new PathPosition(0, p2);
            
        Vampire vampire = new Vampire(PP1);
        Slug slug = new Slug(PP1);
        Shield shield = new Shield(x,y);
        
        boolean wrongResult = shield.checkVampire(slug);
        assertEquals(false, wrongResult);
        double oldChance = Math.round((((Vampire)vampire).getCriticalChance()*100.00) / 100.00);
        assertDoesNotThrow(() -> shield.reduceAttackChance(vampire));
        double newChance = Math.round((((Vampire)vampire).getCriticalChance()*100.00) / 100.00);
        assertEquals(oldChance, newChance);
    }

    /**
     * test if the shield can add defence when it equipped 
     * and test if teh defence value can be reduced when it 
     * is unequipped
     */
    @Test
    public void addFeatureAndunequippedTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        p1.add(pos1);
        PathPosition PP = new PathPosition(0, p1);
    
        Shield shield = new Shield(x,y);
        Character character = new Character(PP);

        int originalcharacterDefence = character.getDefence();
        int expectedDefence = character.getDefence() + shield.getdefence();
        assertDoesNotThrow(() -> shield.addFeature(character));
        assertEquals(expectedDefence, character.getDefence());
        assertDoesNotThrow(() -> shield.unequipped(character));
        assertEquals(originalcharacterDefence, character.getDefence());
    }
}
