package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import org.javatuples.Pair;

public class SuperTower extends Building{
    public final int attack = 15;
    public final int shootingradius = 2;
    
    public SuperTower(SimpleIntegerProperty x, SimpleIntegerProperty y, String type) {
        super(x, y);
        if(type == "SuperTower")super.type = "SuperTower";
    }

    
    public int getAttack(){
        return attack;
    }
    /**
     * chenck if enemy position is in shooting redius of tower
     * @param C
     * @return boolean
     */
    public boolean CharacterPositionCheck(Character C){
        Pair<Integer,Integer> p = C.getPosition();
        if(Math.abs(this.getX() - p.getValue0())<= shootingradius && Math.abs(this.getY() - p.getValue1())<= shootingradius)return true;
        return false;
    }
    /**
     * chenck if enemy position is in shooting redius of tower
     * @param E
     * @return boolean
     */
    public boolean EnemeyPositionCheck(BasicEnemy E){
        Pair<Integer,Integer> p = E.getPosition();
        if(Math.abs(this.getX() - p.getValue0())<= shootingradius && Math.abs(this.getY() - p.getValue1())<= shootingradius)return true;
        return false;        
    }
    /**
     * apply health reduce to enemy
     * @param E
     */
    public void enemiesHPReduce(BasicEnemy E){
        E.addHealth(-attack);
    }
}
