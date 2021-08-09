package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Slug;
import unsw.loopmania.Character;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.Helmet;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class HelmetTest {

    /**
     * create slug and helmet nd check if the slug;s damage reduced
     * with 5 attack damage
     */
    @Test
    public void reducedDamageTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP1 = new PathPosition(0, p2);
    
        Slug slug = new Slug(PP1);
        Helmet helmet = new Helmet(x,y);

        int originalDamage = slug.getDamage();
        System.out.println("old " + slug.getDamage());
        assertDoesNotThrow(() -> helmet.reducedDamage(slug));
        System.out.println("new " + slug.getDamage());
        int newDamage = slug.getDamage();
        assertEquals(originalDamage, newDamage + 5);
    }


    @Test
    public void addFeatureTest() {
        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP = new PathPosition(0, p1);
    
        Helmet helmet = new Helmet(x, y);
        Character character = new Character(PP);

        int oldDamage = character.getDamage();
        int oldDefence = character.getDefence();
        int expectedDamage = character.getDamage()-helmet.getReducedAttack();
        int expectedDefence = character.getDefence() + helmet.getDefence();
        System.out.println("old damage " + character.getDamage() + "old defence " + character.getDefence());
        assertDoesNotThrow(() -> helmet.addFeature(character));
        System.out.println("new damage " + character.getDamage() + "new defence " + character.getDefence());
        assertEquals(expectedDamage, character.getDamage());
        assertEquals(expectedDefence, character.getDefence());
        assertDoesNotThrow(() -> helmet.unequipped(character));
        System.out.println("new1 damage " + character.getDamage() + "new1 defence " + character.getDefence());
        assertEquals(oldDamage, character.getDamage());
        assertEquals(oldDefence, character.getDefence());
    }
}

