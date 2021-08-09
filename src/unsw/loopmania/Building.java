package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
//import javafx.scene.image.Imageview;
import org.javatuples.Pair;


/**
 * a buildong in the world
 * which doesn't move
 */
public class Building extends StaticEntity {
    public String type;
    public PathPosition position;
    //protected Imageview view;
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Pair<Integer,Integer> getPosition() {
        Pair<Integer,Integer> location = new Pair<>(getX(), getY());
        return location;
    }

    public String getType(){
        return this.type;
    }


    //public ImageView getView(){
    //    return view;
    //}


}
