package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.SuperTower;
import unsw.loopmania.Vampire;
import unsw.loopmania.Character;

public class SuperTowerTest {
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
    /**
     * chenck if enemy health is reduced
     */    
    public void Test1(){
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

        SuperTower B = new SuperTower(x,y,"SuperTower");
        Character C = new Character(PP);
        Vampire V = new Vampire(PP1);
        //Enemy
        assertTrue(B.CharacterPositionCheck(C));
        assertTrue(B.EnemeyPositionCheck(V));

    }
}
