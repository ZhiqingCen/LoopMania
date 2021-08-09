package unsw.loopmania;

import org.javatuples.Pair;
import java.util.Random;
import java.lang.String;
/**
 * a basic form of enemy in the world
 */
public class BasicEnemy extends MovingEntity {
    private String name;
    private int health;
    private int damage;
    private int gold;
    private int experience;
    private int battleRange;
    private int supportRange;
    private int direction;
    private double criticalChance;
    private int criticalDamage;
    private int maxHealth;
    private int doggieCoin;
    private PathPosition position;
    public BasicEnemy(PathPosition position) {
        super(position);
        this.position = position;
    }
    public void setDoggieCoin(int doggieCoin) {
        this.doggieCoin = doggieCoin;
    }
    public int getDoggieCoin() {
        return doggieCoin;
    }
    public int getCriticalDamage() {
        return criticalDamage;
    }
    public double getCriticalChance() {
        return criticalChance;
    }
    public void setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
    }
    public void setCriticalDamage(int damage) {
        this.criticalDamage = damage;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDetails(int health, int damage, int gold, int experience, int battleRange, int supportRange) {
        this.health = health;
        this.damage = damage;
        this.gold = gold;
        this.experience = experience;
        this.battleRange = battleRange;
        this.supportRange = supportRange;
        maxHealth = health;
    }
    public int getGold() {
        return gold;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public int getExperience() {
        return experience;
    }
    public int getHealth() {
        return health;
    }
    /**
    * add health to enemy and print message to console
    * @param health
    */
    public void addHealth(int health) {
        if (health+this.health<=0) {
            System.out.println("health changed: -"+this.health);
            this.health = 0;
        }
        else if (health+this.health>=maxHealth) {
            System.out.println("health changed: "+(maxHealth-this.health));
            this.health = maxHealth;
        }
        else {
            System.out.println("health changed: "+health);
            this.health += health;
        }
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getBattleRange() {
        return battleRange;
    }
    public int getSupportRange() {
        return supportRange;
    }
    public Pair<Integer,Integer> getPosition() {
        Pair<Integer,Integer> position = new Pair<>(getX(),getY());
        return position;
    }

    /**
     * move the enemy randomly
     */
    public void move(){
        if (direction == 0){
            moveUpPath();
        }
        else if (direction == 1){
            moveDownPath();
        }
        direction = (new Random()).nextInt(2);
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public PathPosition getPathPosition() {
        return position;
    }
    // public void createEnemies(String type, int totalNumber, List<Integer> counts) {

    // }
}

