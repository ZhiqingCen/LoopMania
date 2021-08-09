package unsw.loopmania;
import org.javatuples.Pair;

/**
* Soldier as character ally
*/
public class Soldier extends MovingEntity{
    private CharacterDetails details;
    private boolean dead;
    public Soldier(PathPosition position) {
        super(position);
        details = new CharacterDetails(50, 5);
        dead = false;
    }
    public Pair<Integer,Integer> getPosition() {
        Pair<Integer,Integer> location = new Pair<>(getX(), getY());
        return location;
    }
    public int getHealth() {
        return details.getHealth();
    }
    public int getDamage() {
        return details.getDamage();
    }
    public boolean getDead() {
        return dead;
    }
    /**
     * add health to soldier
     * @param health
     */
    public void fight(int damage) {
        System.out.print("Soldier health changed: ");
        int health = getHealth() + damage;
        if (health < 0) {
            System.out.println("-"+getHealth());
            details.setHealth(0);
            System.out.println("Soldier dies");
            dead = true;
        }
        else {
            System.out.println(damage);
            details.setHealth(health);
        }
    }
}
