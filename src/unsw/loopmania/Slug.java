package unsw.loopmania;

/**
* Enemy type Slug
*/
public class Slug extends BasicEnemy{
    public Slug(PathPosition position) {
        super(position);
        setDetails(20, 5, 10, 5, 1, 1);
        setName("Slug");
    }
    /**
    * add health to slug and print message to console
    * @param health
    */
    @Override
    public void addHealth(int health) {
        System.out.print("Slug ");
        super.addHealth(health);
    }
}
