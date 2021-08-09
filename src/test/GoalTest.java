package test;

import java.util.ArrayList;
import java.util.Arrays;

import org.javatuples.Pair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import unsw.loopmania.BossGoal;
import unsw.loopmania.ComplexGoal;
import unsw.loopmania.Goal;
import unsw.loopmania.GoalType;
import unsw.loopmania.Item;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Subgoal;

public class GoalTest {
    /**
     * unit test to check if basic goal is met(checkGoal method) for the three mode
     */
    @Test
    public void basicGoalTest() {
        // world 2 goal in the three modes
        Item item = new Item(115, 75, 1); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal standard = new Subgoal(GoalType.GOLD, 100);
        assertEquals(true, standard.checkGoal(world));

        Subgoal survival = new Subgoal(GoalType.CYCLES, 2);
        assertEquals(false, survival.checkGoal(world));

        Subgoal berserker = new Subgoal(GoalType.EXPERIENCE, 50);
        assertEquals(true, berserker.checkGoal(world));
    }

    /**
     * unit test to check complex goal with "and"
     */
    @Test
    public void andGoalTest() {
        // world 6 goal in the standard mode
        Item item = new Item(225, 120, 4); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal gold = new Subgoal(GoalType.GOLD, 200);
        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 100);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(gold);
        goals.add(experience);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.AND, goals);
        assertEquals(true, complexGoal.checkGoal(world));
    }

    /**
     * unit test to check complex goal with "and" failed case
     */
    @Test
    public void andGoalTest2() {
        Item item = new Item(225, 120, 4); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal gold = new Subgoal(GoalType.GOLD, 300);
        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 100);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(gold);
        goals.add(experience);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.AND, goals);
        assertEquals(false, complexGoal.checkGoal(world));
    }

    /**
     * unit test to check complex goal with "or"
     */
    @Test
    public void orGoalTest() {
        // world 6 goal in the standard mode
        Item item = new Item(175, 120, 4); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal gold = new Subgoal(GoalType.GOLD, 200);
        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 100);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(gold);
        goals.add(experience);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.OR, goals);
        assertEquals(true, complexGoal.checkGoal(world));
    }

    /**
     * unit test to check complex goal with "or" failed case
     */
    @Test
    public void orGoalTest2() {
        // world 6 goal in the standard mode
        Item item = new Item(175, 120, 4); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal gold = new Subgoal(GoalType.GOLD, 200);
        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 150);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(gold);
        goals.add(experience);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.OR, goals);
        assertEquals(false, complexGoal.checkGoal(world));
    }

    /**
     * check goal with LoopManiaWorld, and setGoalCOndition method
     */
    @Test
    public void checkGoalInWorldTest() {
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Item item = new Item(115, 75, 1); // make up some value
        world.setItem(item);

        Subgoal standard = new Subgoal(GoalType.GOLD, 100);
        assertEquals(true, standard.checkGoal(world));
        world.setGoalCondition(standard);
        assertEquals(standard, world.getGoal());
        assertEquals(true, standard.checkGoal(world));
    }

    /**
     * check goalType getGoalType()
     */
    @Test
    public void checkGoalTypeTest() {
        GoalType goal = GoalType.AND;
        assertEquals("and", goal.getGoalType());
    }

    /**
     * subGoal check invalid goalType
     */
    @Test
    public void subgoalCheckGoalErrorTest() {
        Goal subGoal = new Subgoal(GoalType.AND, 1);
        Item item = new Item(115, 75, 1); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);
        assertEquals(false, subGoal.checkGoal(world));
    }

    /**
     * complex check invalid goalType
     */
    @Test
    public void complexGoalCheckGoalErrorTest() {
        // world 6 goal in the standard mode
        Item item = new Item(175, 120, 4); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal gold = new Subgoal(GoalType.GOLD, 100);
        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 100);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(gold);
        goals.add(experience);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.CYCLES, goals);
        assertEquals(false, complexGoal.checkGoal(world));
    }

    /**
     * check complex goal with bosses (only Doggie) does not met
     */
    @Test
    public void bossGoalTestDoggieNotDefeated() {
        Item item = new Item(175, 120, 20); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal cycles = new Subgoal(GoalType.CYCLES, 20);
        BossGoal doggie = new BossGoal(GoalType.BOSSES);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(cycles);
        goals.add(doggie);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.AND, goals);
        assertEquals(true, cycles.checkGoal(world));
        assertEquals(true, world.getIsDoggie());
        assertEquals(false, world.getIsFinalBoss());
        assertEquals(false, world.checkBossDefeated());
        assertEquals(false, complexGoal.checkGoal(world));
    }

    /**
     * check complex goal with the two bosses does not met
     */
    @Test
    public void bossGoalTestAllBossesNotDefeated() {
        Item item = new Item(1000, 10000, 40); // make up some value
        LoopManiaWorld world = new LoopManiaWorld(8, 8, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        world.setItem(item);

        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 10000);
        Subgoal cycles = new Subgoal(GoalType.CYCLES, 40);
        BossGoal doggie = new BossGoal(GoalType.BOSSES);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(experience);
        goals.add(cycles);
        goals.add(doggie);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.AND, goals);
        assertEquals(true, experience.checkGoal(world));
        assertEquals(true, cycles.checkGoal(world));
        assertEquals(true, world.getIsDoggie());
        assertEquals(true, world.getIsFinalBoss());
        assertEquals(false, world.checkBossDefeated());
        assertEquals(false, complexGoal.checkGoal(world));
    }

    @Test
    public void goalToStringTest() {
        Subgoal gold = new Subgoal(GoalType.GOLD, 100);
        Subgoal experience = new Subgoal(GoalType.EXPERIENCE, 10000);
        Subgoal cycles = new Subgoal(GoalType.CYCLES, 40);
        BossGoal boss = new BossGoal(GoalType.BOSSES);
        ArrayList<Goal> goals = new ArrayList<Goal>();
        goals.add(gold);
        goals.add(experience);
        goals.add(cycles);
        goals.add(boss);

        ComplexGoal complexGoal = new ComplexGoal(GoalType.AND, goals);
        assertEquals("gold: 100", gold.toString());
        assertEquals("experience: 10000", experience.toString());
        assertEquals("cycles: 40", cycles.toString());
        assertEquals("bosses", boss.toString());
        assertEquals("gold: 100 and\nexperience: 10000 and\ncycles: 40 and\nbosses", complexGoal.toString());
    }
}
