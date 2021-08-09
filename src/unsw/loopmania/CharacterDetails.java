package unsw.loopmania;

/**
* provide details of character and soldier
*/
public class CharacterDetails {
    private int health;
    private int damage;
    public CharacterDetails(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
