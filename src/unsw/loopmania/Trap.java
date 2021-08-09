package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

import org.javatuples.Pair;
/**
 * a basic form of building in the world
 */
public class Trap extends Building {
    public final int attack = 5;
    

    public Trap(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        if(type == "Trap")super.type = "Trap";
    }

    
    public int getAttack(){
        return attack;
    }
    
    /**
     * chenck if enemy position is equal to trap
     * @param E
     * @return boolean
     */
    public boolean EnemeyPositionCheck(BasicEnemy E){
        Pair<Integer,Integer> pos = E.getPosition();
        if(pos.getValue0() == this.getX() && pos.getValue1() == this.getY())return true;
        return false;        
    }
    /**
     * apply health reduce to Enemy
     * @param E
     */
    public void enemiesHPReduce(BasicEnemy E){
        E.addHealth(- attack);
    }
}
