package unsw.loopmania;

import java.util.Random;

public class Doggie extends BasicEnemy{
    public Doggie(PathPosition position) {
        super(position);
        setDetails(80, 15, 0, 1000, 1, 1);
        super.setCriticalChance(0.05);
        setName("Doggie");
        super.setDoggieCoin(50);
    }
    public int getDoggieCoin() {
        return super.getDoggieCoin();
    }
    /**
    * add health to slug and print message to console
    * @param health
    */
    @Override
    public void addHealth(int health) {
        System.out.print("Doggie ");
        super.addHealth(health);
    }
}
