package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Map;

public class MapTest{
    public Map new_map = new Map();

    @Test
    public void CheckPathMap(){
        assertEquals(true, new_map.CheckPath(6, 2));
        assertEquals(true, new_map.CheckPath(1, 2));
        assertEquals(true, new_map.CheckPath(5, 6));
        assertEquals(false, new_map.CheckPath(5, 1));
    }

    @Test
    public void TestGetRange(){
        Pair<Integer,Integer> pos = new Pair<Integer,Integer>(5, 3);
        List<Pair<Integer,Integer>> range = new ArrayList<>();
        Pair<Integer,Integer> pos1 = new Pair<Integer,Integer>(4, 4);
        Pair<Integer,Integer> pos2 = new Pair<Integer,Integer>(5, 4);
        Pair<Integer,Integer> pos3 = new Pair<Integer,Integer>(6, 4);
        range.add(pos1);
        range.add(pos2);
        range.add(pos3);
        assertEquals(range, new_map.getRange(pos, 1));
    }
}