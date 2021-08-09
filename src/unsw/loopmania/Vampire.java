package unsw.loopmania;

/**
* Enemy type Vampire
*/
public class Vampire extends BasicEnemy{
    public Vampire(PathPosition position) {
        super(position);
        setDetails(50, 20, 100, 50, 2, 3);
        super.setCriticalChance(0.1);
        super.setCriticalDamage(40);
        setName("Vampire");
    }
    /**
    * add health to vampire and print message to console
    * @param health
    */
    @Override
    public void addHealth(int health) {
        System.out.print("Vampire ");
        super.addHealth(health);
    }
}

