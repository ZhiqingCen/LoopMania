package unsw.loopmania;
import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.List;
/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private PathPosition position;
    private CharacterDetails details;
    private int defence;
    private int maxHealth;
    private Item items;
    private List<Soldier> allies;
    private int lives;
    private boolean dead;
   
    public Character(PathPosition position) {
        super(position);
        details = new CharacterDetails(100, 10);
        maxHealth = 100;
        this.position = position;
        items = new Item();
        allies = new ArrayList<>();
        defence = 0;
        lives = 1;
        dead = false;
    }
    public PathPosition getPathPosition() {
        return position;
    }
    public Pair<Integer,Integer> getPosition() {
        Pair<Integer,Integer> location = new Pair<>(getX(), getY());
        return location;
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
        System.out.println("lives now: "+this.lives);
    }
    /**
     * reduce the character health, check if the character loses the battle
     * @param damage
     */
    public void fight(int damage) {
        if (defence > damage) {
            damage = 0;
        }
        else {
            addHealth(defence-damage);
        }
        if (getHealth()<=0) {
            lives -= 1;
            System.out.println("lives now: "+ lives);
            if (lives > 0) {
                addHealth(maxHealth);
            }
            else {
                dead = true;
                System.out.println("Character loses the battle");
            }
        }
    }
    public boolean getDead() {
        return dead;
    }
    public int getGold() {
        return items.getGold();
    }
    public CharacterDetails getCharacterDetails(){
        return details;
    }
    /**
    * add gold to character's gold and print message to console
    * @param gold
    */
    public void addGold(int gold) {
        items.setGold(gold+items.getGold());
        System.out.println("Character gold "+gold+" changed");
    }
    public void setDefence(int defence) {
        this.defence = defence;
    }
    /**
    * add health to character's health and print message to console
    * @param health
    */
    public void addHealth(int health) {
        int totalHealth = getHealth() + health;
        if (totalHealth > maxHealth) {
            int addedHealth = maxHealth - getHealth();
            details.setHealth(maxHealth);
            System.out.println("Character health "+addedHealth+" changed");
        }
        else {
            details.setHealth(totalHealth);
            System.out.println("Character health "+health);
        }
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int health) {
        maxHealth = health;
    }
    /**
    * add maximum health to character's health upbound and print message to console
    * @param health
    */
    public void addMaxHealth(int health) {
        setMaxHealth(health+maxHealth);
        System.out.println("Character: Total health changed to "+getMaxHealth());
    }
    public int getHealth() {
        return details.getHealth();
    }
    public int getDamage() {
        return details.getDamage();
    }
    /**
    * add damage to character's damage and print message to console
    * @param damage
    */
    public void addDamage(int damage) {
        details.setDamage(damage+details.getDamage());
        System.out.println("Character damage "+damage);
    }
    public int getDefence() {
        return defence;
    }
    /**
    * add defence to character's defence and print message to console
    * @param defence
    */
    public void addDefence(int defence) {
        this.defence += defence;
        System.out.println("Character defence "+defence);
    }
    /**
    * add one ally to character and print message to console
    * @param defence
    */
    public boolean addAlly() {
        if (allies.size() < 2) {
            Soldier soldier = new Soldier(position);
            allies.add(soldier);
            System.out.println("Character one ally added");
            return true;
        }
        return false;
    }
    public List<Soldier> getAlly() {
        return allies;
    }
}
