package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Slug;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.Character;
import unsw.loopmania.Staff;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class StaffTest {

    /**
     * create slug and staff to check if the enemy change and 
     * get hurt from itself
     */
    @Test
    public void changeEnemyTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p2.add(pos2);
        PathPosition PP1 = new PathPosition(0, p2);

        Slug slug = new Slug(PP1);
        Staff staff = new Staff(x,y);

        assertDoesNotThrow(() -> staff.setRandom(2));
        assertDoesNotThrow(() -> staff.changeEnemy(slug));
        boolean result = false;
        if (slug.getHealth() <= 0) {
            result = true;
        }
        assertEquals(true, result);
    }

    /**
     * test if the Staff can add scale vaue damage to character
     * and test if the scale value damage to character can be reduced 
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

        Staff staff = new Staff(x,y);
        Character character = new Character(PP);

        int originalcharacterDamage = character.getDamage();
        int expectedDamage = character.getDamage() + staff.getDamage();
        assertDoesNotThrow(() -> staff.addFeature(character));
        assertEquals(expectedDamage, character.getDamage());
        assertDoesNotThrow(() -> staff.unequipped(character));
        assertEquals(originalcharacterDamage, character.getDamage());
    }
}
