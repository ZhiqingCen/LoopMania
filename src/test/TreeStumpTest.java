package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.Character;
import unsw.loopmania.Doggie;
import unsw.loopmania.FinalBoss;
import unsw.loopmania.TreeStump;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;

import org.javatuples.Pair;

public class TreeStumpTest {

    /**
     * tripleDamageTest to check if the the tree stump triple defence to boss
     * and check if it can add normal defence succefully
     */
    @Test
    public void tripleDamageTest() {

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
        Doggie doggie = new Doggie(PP1);
        FinalBoss boss = new FinalBoss(PP1);
        Character character = new Character(PP);
        TreeStump treeStump = new TreeStump(x,y);
        assertEquals(false, treeStump.checkBoss(slug));
        assertEquals(true, treeStump.checkBoss(doggie));
        assertEquals(true, treeStump.checkBoss(boss));

        int originalcharacterDefence = character.getDefence();
        int characterDefenceEquip = character.getDefence() + treeStump.getDefence();
        int expectedDefenceToBoss = character.getDefence() * 2;
        assertDoesNotThrow(() -> treeStump.addFeatureBoss(character));
        assertEquals(expectedDefenceToBoss, character.getDefence());
        assertDoesNotThrow(() -> treeStump.unequippedBoss(character));
        assertEquals(originalcharacterDefence, character.getDefence());
        assertDoesNotThrow(() -> treeStump.addFeature(character));
        assertEquals(characterDefenceEquip, character.getDefence());
        assertDoesNotThrow(() -> treeStump.unequipped(character));
        assertEquals(originalcharacterDefence, character.getDefence());
    }
}
