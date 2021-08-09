package test;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Soldier;

import java.util.List;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
* test if getPosition can correctly return position
**/
public class SoldierTest {
    private Soldier soldier;
    private Pair<Integer,Integer> pos1;
    private PathPosition position;
    public void initialize() {
        pos1 = new Pair<Integer,Integer>(0, 0);
        List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(pos1);
        position = new PathPosition(0, orderedPath);
        soldier = new Soldier(position);
    }
    @Test
    public void getPositionTest() {
        initialize();
        assertEquals(pos1,soldier.getPosition());
    }
    @Test
    public void fightTest() {
        initialize();
        soldier.fight(-5);
        assertEquals(45, soldier.getHealth());
        soldier.fight(-100);
        assertEquals(0, soldier.getHealth());
    }
}

