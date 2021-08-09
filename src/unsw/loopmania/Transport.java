package unsw.loopmania;

//import unsw.loopmania.Map;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Transport extends Equipment {

    private final static String name = "Transport";

    public Transport(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }
    
    /**
     *  if the character is not in heroscastle, the character can use tp to transport to heroscastle
     * @param character
     * @return boolean
     */
    public void tpForward(LoopManiaWorld world){
        
        for(int i = 0; i<8; i++){
            world.runTickMoves();
        }
    }
}
