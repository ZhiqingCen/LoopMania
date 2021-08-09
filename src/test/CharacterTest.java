package test;
import unsw.loopmania.Character;
import unsw.loopmania.CharacterDetails;
import unsw.loopmania.PathPosition;

import java.util.List;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
* tests for Character
**/
public class CharacterTest {
    public Character character;
    public CharacterDetails characterDetails = new CharacterDetails(100, 10);;
    public Pair<Integer,Integer> pos1 = new Pair<>(0,0);
    public Pair<Integer,Integer> pos2 = new Pair<>(0,1);
    public Pair<Integer,Integer> pos3 = new Pair<>(0,2);
    public List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    public PathPosition characterPosition;
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
    /*
    * test if it can return correct position
    **/
    @Test
    public void getPositionTest() {
        initialize();
        assertEquals(pos1, character.getPosition());
    }
    /*
    * add gold and test for the amount
    **/
    @Test
    public void addGoldTest() {
        initialize();
        character.addGold(10);
        assertEquals(10, character.getGold());
    }
    /*
    * lose health and test for the amount
    **/
    @Test
    public void addHealthTest() {
        initialize();
        character.addHealth(-10);
        assertEquals(90, character.getHealth());
    }
    /*
    * add health and test for the upper bound
    **/
    @Test
    public void addMaxHealthTest() {
        initialize();
        character.addHealth(10);
        assertEquals(100, character.getHealth());
    }
    /*
    * add damage and test the amount
    **/
    @Test
    public void addDamageTest() {
        initialize();
        character.addDamage(10);
        assertEquals(20, character.getDamage());
    }
    /*
    * add defence and test the amount
    **/
    @Test
    public void addDefenceTest() {
        initialize();
        character.addDefence(10);
        assertEquals(10, character.getDefence());
    }
    /*
    * add ally and test if there is ally in list
    **/
    @Test
    public void addAllyTest() {
        initialize();
        character.addAlly();
        assertEquals(1, character.getAlly().size());
        character.addAlly();
        assertEquals(2, character.getAlly().size());
        character.addAlly();
        assertEquals(2, character.getAlly().size());
    }
    /*
    * add maximum health bound and test for the amount
    **/
    @Test
    public void addToMaxHealthTest() {
        initialize();
        character.addMaxHealth(10);
        assertEquals(110, character.getMaxHealth());
    }
    @Test
    public void fightTestDamage() {
        initialize();
        character.fight(10);
        assertEquals(90, character.getHealth());
    }
    // private final PrintStream originalErr = System.err;
    /**
     * test if the character loses the battle
     */
    @Test
    public void fightTestDead() {
        initialize();
        character.fight(100);
        // assertThrows(ArithmeticException.class, ()->character.fight(100));
        assertEquals(true, character.getDead());
        assertEquals(0, character.getLives());
        
        // ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        // System.setErr(new PrintStream(errContent));
        // character.fight(100);
        // System.setErr(originalErr);
        // String str = errContent.toString();
        // System.out.println(str);
        // assertEquals("Character loses the battle, Game over", str);
        // assertEquals("Character loses the battle, Game over\n", errContent.toString());
    }
    @Test 
    public void fightLiveTest() {
        initialize();
        character.setLives(2);
        character.fight(100);
        assertEquals(1, character.getLives());
        assertEquals(character.getMaxHealth(), character.getHealth());
    }
}

