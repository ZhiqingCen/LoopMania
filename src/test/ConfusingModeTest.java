package test;

import org.junit.Test;

import org.javatuples.Pair;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.*;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;

public class ConfusingModeTest {

    private Character character;
    private Pair<Integer,Integer> pos1 = new Pair<>(0,0);
    private Pair<Integer,Integer> pos2 = new Pair<>(0,1);
    private Pair<Integer,Integer> pos3 = new Pair<>(0,2);
    private List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    private PathPosition characterPosition;
    
    /*
    * create a new character
    **/
    public void initialize() {
        orderedPath.add(pos1);
        orderedPath.add(pos2);
        orderedPath.add(pos3);
        characterPosition = new PathPosition(0, orderedPath); 
        character = new Character(characterPosition);
    }

    /**
     * test to create random features of TheOneRing in confusing mode
     * features random from all rare items except for TheOneRing
     */
    @Test
    public void ringUpdateConfusingModeTest() {
        ConfusingMode mode = new ConfusingMode("TheOneRing");
        assertDoesNotThrow(() -> mode.updateConfusingMode(0));        
        assertTrue(mode.getConfusingTreeStump());
        assertFalse(mode.getConfusingRing());
        assertFalse(mode.getConfusingAnduril());

        assertDoesNotThrow(() -> mode.updateConfusingMode(1));        
        assertTrue(mode.getConfusingAnduril());
        assertFalse(mode.getConfusingTreeStump());
        assertFalse(mode.getConfusingRing());
    }

    /**
     * test to create random features of Anduril in confusing mode
     * features random from all rare items except for Anduril
     */
    @Test
    public void andurilUpdateConfusingModeTest() {
        ConfusingMode mode = new ConfusingMode("Anduril");
        assertDoesNotThrow(() -> mode.updateConfusingMode(0));        
        assertTrue(mode.getConfusingRing());
        assertFalse(mode.getConfusingTreeStump());
        assertFalse(mode.getConfusingAnduril());

        assertDoesNotThrow(() -> mode.updateConfusingMode(1));        
        assertTrue(mode.getConfusingTreeStump());
        assertFalse(mode.getConfusingAnduril());
        assertFalse(mode.getConfusingRing());
    }

    /**
     * test to create random features of TreeStump in confusing mode
     * features random from all rare items except for TreeStump
     */
    @Test
    public void treeStumpUpdateConfusingModeTest() {
        ConfusingMode mode = new ConfusingMode("TreeStump");
        assertDoesNotThrow(() -> mode.updateConfusingMode(0));        
        assertTrue(mode.getConfusingAnduril());
        assertFalse(mode.getConfusingTreeStump());
        assertFalse(mode.getConfusingRing());

        assertDoesNotThrow(() -> mode.updateConfusingMode(1));        
        assertTrue(mode.getConfusingRing());
        assertFalse(mode.getConfusingAnduril());
        assertFalse(mode.getConfusingTreeStump());
    }

    /**
     * test for creating random features of TheOneRing in confusing mode
     * test if added features works normally
     */
    @Test
    public void ringEquipConfusingModeTest() {
        ConfusingMode mode = new ConfusingMode("TheOneRing");
        character = new Character(characterPosition);

        assertDoesNotThrow(() -> mode.updateConfusingMode(0));        
        assertTrue(mode.getConfusingTreeStump());
        assertDoesNotThrow(() -> mode.addFeature(character));
        assertEquals(15, character.getDefence());
        assertDoesNotThrow(() -> mode.addBossFeature(character));
        assertEquals(30, character.getDefence());
        assertDoesNotThrow(() -> mode.removeBossFeature(character));
        assertEquals(15, character.getDefence());
        assertDoesNotThrow(() -> mode.removeFeature(character));
        assertEquals(0, character.getDefence());

        assertDoesNotThrow(() -> mode.updateConfusingMode(1));        
        assertTrue(mode.getConfusingAnduril());
        assertDoesNotThrow(() -> mode.addFeature(character));
        assertEquals(25, character.getDamage());
        assertDoesNotThrow(() -> mode.addBossFeature(character));
        assertEquals(75, character.getDamage());
        assertDoesNotThrow(() -> mode.removeBossFeature(character));
        assertEquals(25, character.getDamage());
        assertDoesNotThrow(() -> mode.removeFeature(character));
        assertEquals(10, character.getDamage());
    }

    /**
     * test for creating random features of Anduril in confusing mode
     * test if added features works normally
     */
    @Test
    public void andurilEquipConfusingModeTest() {
        ConfusingMode mode = new ConfusingMode("Anduril");
        character = new Character(characterPosition);

        assertDoesNotThrow(() -> mode.updateConfusingMode(0));        
        assertTrue(mode.getConfusingRing());
        assertDoesNotThrow(() -> mode.addFeature(character));
        assertEquals(2, character.getLives());
        assertDoesNotThrow(() -> mode.removeFeature(character));
        assertEquals(1, character.getLives());
        assertDoesNotThrow(() -> mode.addBossFeature(character));
        assertDoesNotThrow(() -> mode.removeBossFeature(character));

        assertDoesNotThrow(() -> mode.updateConfusingMode(1));        
        assertTrue(mode.getConfusingTreeStump());
        assertDoesNotThrow(() -> mode.addFeature(character));
        assertEquals(15, character.getDefence());
        assertDoesNotThrow(() -> mode.addBossFeature(character));
        assertEquals(30, character.getDefence());
        assertDoesNotThrow(() -> mode.removeBossFeature(character));
        assertEquals(15, character.getDefence());
        assertDoesNotThrow(() -> mode.removeFeature(character));
        assertEquals(0, character.getDefence());
    }

    /**
     * test for creating random features of TreeStump in confusing mode
     * test if added features works normally
     */
    @Test
    public void treeStumpEquipConfusingModeTest() {
        ConfusingMode mode = new ConfusingMode("TreeStump");
        character = new Character(characterPosition);

        assertDoesNotThrow(() -> mode.updateConfusingMode(0));        
        assertTrue(mode.getConfusingAnduril());
        assertDoesNotThrow(() -> mode.addFeature(character));
        assertEquals(25, character.getDamage());
        assertDoesNotThrow(() -> mode.addBossFeature(character));
        assertEquals(75, character.getDamage());
        assertDoesNotThrow(() -> mode.removeBossFeature(character));
        assertEquals(25, character.getDamage());
        assertDoesNotThrow(() -> mode.removeFeature(character));
        assertEquals(10, character.getDamage());

        assertDoesNotThrow(() -> mode.updateConfusingMode(1));        
        assertTrue(mode.getConfusingRing());
        assertDoesNotThrow(() -> mode.addFeature(character));
        assertEquals(2, character.getLives());
        assertDoesNotThrow(() -> mode.removeFeature(character));
        assertEquals(1, character.getLives());
        assertDoesNotThrow(() -> mode.addBossFeature(character));
        assertDoesNotThrow(() -> mode.removeBossFeature(character));
    }
}
