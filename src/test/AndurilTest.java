package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.Character;
import unsw.loopmania.Doggie;
import unsw.loopmania.FinalBoss;
import unsw.loopmania.Anduril;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;

import org.javatuples.Pair;

public class AndurilTest {

    /**
     * test if the anduril can triple damage when it equipped
     * test if the change corrctly when equip and unequip the anduril
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
        Anduril anduril = new Anduril(x,y);
        assertEquals(false, anduril.checkBoss(slug));
        assertEquals(true, anduril.checkBoss(doggie));
        assertEquals(true, anduril.checkBoss(boss));

        int originalcharacterDamage = character.getDamage();
        int characterDamageEquip = character.getDamage() + anduril.getDamage();
        int expectedDamageToBoss = character.getDamage() * 3;
        assertDoesNotThrow(() -> anduril.addFeatureBoss(character));
        assertEquals(expectedDamageToBoss, character.getDamage());
        assertDoesNotThrow(() -> anduril.unequippedBoss(character));
        assertEquals(originalcharacterDamage, character.getDamage());
        assertDoesNotThrow(() -> anduril.addFeature(character));
        assertEquals(characterDamageEquip, character.getDamage());
        assertDoesNotThrow(() -> anduril.unequipped(character));
        assertEquals(originalcharacterDamage, character.getDamage());
    }

}
