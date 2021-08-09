package test;

import java.util.*;

import org.junit.jupiter.api.Test;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Vampire;
import unsw.loopmania.Slug;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.Character;
import unsw.loopmania.Stake;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;

public class StakeTest {

    /**
     * create vampire and slug and stake to check when the slug meet
     * stake it will return false, but when the vampire meet stake will 
     * return ture
     */
    @Test
    public void checkVampireTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        List<Pair<Integer,Integer>> p2 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        p1.add(pos1);
        p2.add(pos2);
        PathPosition PP1 = new PathPosition(0, p2);

        Vampire vampire = new Vampire(PP1);
        Slug slug = new Slug(PP1);
        Stake stake = new Stake(x,y);

        boolean slugcheck = stake.checkVampire(slug);
        assertNotEquals(slugcheck, true);

        boolean vampirecheck = stake.checkVampire(vampire);
        assertEquals(vampirecheck, true);
    }

    /**
     * test if the stake can add vampire damage when it equipped
     * test if the change corrctly when equip and unequip the stake
     */
    @Test
    public void vampireDamageTest() {

        SimpleIntegerProperty x = new SimpleIntegerProperty(0);
        SimpleIntegerProperty y = new SimpleIntegerProperty(0);
        List<Pair<Integer,Integer>> p1 = new ArrayList<Pair<Integer,Integer>>();
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        p1.add(pos1);
        PathPosition PP = new PathPosition(0, p1);

        Character character = new Character(PP);
        Stake stake = new Stake(x,y);

        int originalcharacterDamage = character.getDamage();
        int characterDamageEquip = character.getDamage() + stake.getDamage();
        int expectedDamageToBoss = character.getDamage() + stake.getVampireDamage();
        assertDoesNotThrow(() -> stake.addFeatureVampire(character));
        assertEquals(expectedDamageToBoss, character.getDamage());
        assertDoesNotThrow(() -> stake.unequippedVampire(character));
        assertEquals(originalcharacterDamage, character.getDamage());
        assertDoesNotThrow(() -> stake.addFeature(character));
        assertEquals(characterDamageEquip, character.getDamage());
        assertDoesNotThrow(() -> stake.unequipped(character));
        assertEquals(originalcharacterDamage, character.getDamage());
    }
}
