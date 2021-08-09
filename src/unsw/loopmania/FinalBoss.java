package unsw.loopmania;

public class FinalBoss extends BasicEnemy{
    public FinalBoss(PathPosition position) {
        super(position);
        setDetails(80, 40, 0, 0, 1, 1);
        super.setCriticalChance(0.05);
        setName("FinalBoss");
        super.setDoggieCoin(50);
    }
    /**
    * add health to slug and print message to console
    * @param health
    */
    @Override
    public void addHealth(int health) {
        System.out.print("Elan Muske ");
        super.addHealth(health);
    }
}
