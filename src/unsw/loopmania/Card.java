package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
//import javafx.scene.image.Imageview;

/**
 * a Card in the world
 * which doesn't move
 */
public class Card extends StaticEntity {
    public String type;
    public int idInSlot;
    //protected Imageview view;
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

    }

    public String getType(){
        return this.type;
    }

//    public ImageView getView(){
//        return view;
//    }

}
