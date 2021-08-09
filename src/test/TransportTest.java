package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Transport;
import unsw.loopmania.Character;

public class TransportTest {
    
    public List<Pair<Integer,Integer>> createOrderedPath() {
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        for (int cell=1; cell<=6; ++cell) {
            Pair<Integer,Integer> road= new Pair<>(1, cell);
            orderedPath.add(road);   
        }
        for (int cell=2; cell<=6; ++cell) {
            Pair<Integer,Integer> road= new Pair<>(cell, 6);
            orderedPath.add(road);   
        }
        for (int cell=5; cell>=1; --cell) {
            Pair<Integer,Integer> road= new Pair<>(6, cell);
            orderedPath.add(road);   
        }
        for (int cell=5; cell>=2; --cell) {
            Pair<Integer,Integer> road= new Pair<>(cell, 1);
            orderedPath.add(road);   
        }
        return orderedPath;
    }

    public LoopManiaWorld newWorld = new LoopManiaWorld(8, 8, createOrderedPath());
    public PathPosition characterPosition = new PathPosition(0, createOrderedPath()); 
    public Character character = new Character(characterPosition);

    @Test
    public void testTpForward(){
        Transport tp = new Transport(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        
        assertDoesNotThrow(() -> tp.tpForward(newWorld));
        newWorld.addEquippedInventoryItem(tp);
    }
}
