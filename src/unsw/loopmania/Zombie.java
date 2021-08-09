package unsw.loopmania;

/**
* Enemy type Zombie
*/
public class Zombie extends BasicEnemy{
    public Zombie(PathPosition position) {
        super(position);
        setDetails(30, 10, 50, 25, 2, 2);
        super.setCriticalChance(0.05);
        setName("Zombie");
    }
    /**
    * add health to zombie and print message to console
    * @param health
    */
    @Override
    public void addHealth(int health) {
        System.out.print("Zombie ");
        super.addHealth(health);
    }
}

