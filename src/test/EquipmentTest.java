package test;

import java.util.Arrays;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import unsw.loopmania.LoopManiaWorld;

public class EquipmentTest {

    LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));

    /**
     * test if the sword added to unequip inventory
     */
    @Test
    public void TestItemAdded() {
        assertDoesNotThrow(() ->  world.spawnUnequippedSwordOrHealthPotion(0));
    }
    
}
