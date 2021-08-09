package test;
import unsw.loopmania.Zombie;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Slug;
import unsw.loopmania.Vampire;
import unsw.loopmania.Doggie;
import unsw.loopmania.FinalBoss;
import unsw.loopmania.PathPosition;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/*
* test for enemies
**/
public class EnemyTest {
    /*
    * create and test if the enemies can move randomly
    **/
    private List<BasicEnemy> enemies = new ArrayList<>();
    private List<Pair<Integer,Integer>> orderedPath = new ArrayList<>();
    private Slug slug;
    private Vampire vampire;
    private Zombie zombie;
    private Doggie doggie;
    private FinalBoss boss;
    /**
     * set up initial conditions
     */
    public void initialize() {
        Pair<Integer,Integer> pos1 = new Pair<>(0,0);
        Pair<Integer,Integer> pos2 = new Pair<>(0,1);
        Pair<Integer,Integer> pos3 = new Pair<>(0,2);
        Pair<Integer,Integer> pos4 = new Pair<>(0,3);
        orderedPath.add(pos1);
        orderedPath.add(pos2);
        orderedPath.add(pos3);
        orderedPath.add(pos4);
        PathPosition position = new PathPosition(0, orderedPath);
        slug = new Slug(position);
        vampire = new Vampire(position);
        zombie = new Zombie(position);
        doggie = new Doggie(position);
        boss = new FinalBoss(position);
        
        enemies.add(slug);
        enemies.add(vampire);
        enemies.add(zombie);
        enemies.add(doggie);
        enemies.add(boss);
    }
    /**
     * add health and test amount of health
     */
    @Test
    public void addHealthTest() {
        initialize();
        // test basic cases
        for (BasicEnemy enemy:enemies) {
            int originHealth = enemy.getHealth();
            enemy.addHealth(-5);
            int presentHealth = enemy.getHealth();
            assertEquals(originHealth-5, presentHealth);
        }
        // test less than zero health
        for (BasicEnemy enemy:enemies) {
            enemy.addHealth(-100);
            int presentHealth = enemy.getHealth();
            assertEquals(0, presentHealth);
        }
        // test greater than maxHealth
        for (BasicEnemy enemy:enemies) {
            enemy.addHealth(100);
            int presentHealth = enemy.getHealth();
            assertEquals(enemy.getMaxHealth(), presentHealth);
        }
    }
    /**
     * test if enemy moves randomly
     */
    @Test
    public void moveTest() {
        initialize();
        for (BasicEnemy enemy:enemies) {
            Pair<Integer,Integer> originalPos = enemy.getPosition();
            enemy.move();
            Pair<Integer,Integer> afterPos = enemy.getPosition();
            System.out.println(afterPos);
            assertNotEquals(originalPos,afterPos);
            boolean found = false;
            if (orderedPath.contains(afterPos) && (!afterPos.equals(originalPos))) {
                found = true;
            }
            assertTrue(found);
        }
    }
}

