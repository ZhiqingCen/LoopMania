package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.Character;
import unsw.loopmania.Sword;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;


public class SwordTest {

    /**
     * test if the sword can add scale vaue damage to character
     * and test if the scale vaue damage to character can be reduced 
     * when unequipped
     */
    @Test
    public void addFeatureAndunequippedTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        p1.add(pos1);
        PathPosition PP = new PathPosition(0, p1);

        Sword sword = new Sword(x,y);
        Character character = new Character(PP);

        int originalcharacterDamage = character.getDamage();
        int expectedDamage = character.getDamage() + sword.getDamage();
        assertDoesNotThrow(() -> sword.addFeature(character));
        assertEquals(expectedDamage, character.getDamage());
        assertDoesNotThrow(() -> sword.unequipped(character));
        assertEquals(originalcharacterDamage, character.getDamage());
    }
}

