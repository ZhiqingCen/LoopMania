package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Slug;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.Character;
import unsw.loopmania.Armour;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class ArmourTest {

    /**
     * halfAttackTest create slug and armour
     * and to check if the half the attack damage of slug
     * check addfeature and unequip to the charactr defence change
     */
    @Test
    public void halfAttackTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP = new PathPosition(0, p1);
        PathPosition PP1 = new PathPosition(0, p2);
    
        Slug slug = new Slug(PP1);
        Armour armour = new Armour(x,y);
        Character character = new Character(PP);

        int originalDamage = slug.getDamage();
        assertDoesNotThrow(() -> armour.halfAttack(slug));

        boolean result = false;
        if (slug.getDamage() == (originalDamage/2)) {
            result = true;
        }
        assertEquals(result, true);

        int originalcharacterDefence = character.getDefence();
        int expectedDefence = character.getDefence() + armour.getdefence();
        assertDoesNotThrow(() -> armour.addFeature(character));
        assertEquals(expectedDefence, character.getDefence());
        assertDoesNotThrow(() -> armour.unequipped(character));
        assertEquals(originalcharacterDefence, character.getDefence());
    }
}
