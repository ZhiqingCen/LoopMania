package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.Character;
import unsw.loopmania.TheOneRing;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class TheOneRingTest {
    /**
     * create character and the one ring to check if the character
     * 's live add one time
     */
    @Test
    public void makeLiveTest() {

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
        TheOneRing theOneRing = new TheOneRing(x,y);
        theOneRing.makeLive(character);
        assertEquals(2, character.getLives());
    }

    /**
     * create character and the one ring to check if the character
     * 's live add one time, and check if the live would be reduce one
     * after unequipped the ring
     */
    @Test
    public void unequippedTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP = new PathPosition(0, p1);

        TheOneRing theOneRing = new TheOneRing(x,y);
        Character character = new Character(PP);

        int originalcharacterlive = character.getLives();
        int expectedlives = character.getLives() + 1;
        assertDoesNotThrow(() -> theOneRing.makeLive(character));
        assertEquals(expectedlives, character.getLives());
        assertDoesNotThrow(() -> theOneRing.unequipped(character));
        assertEquals(originalcharacterlive, character.getLives());
    }
}

