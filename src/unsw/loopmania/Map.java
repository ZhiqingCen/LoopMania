package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Map{

    static BooleanProperty[][] map = new BooleanProperty[8][8];

    public Map(){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i == 1 && j <= 6 && j > 0){
                    map[i][j] = new SimpleBooleanProperty(true);
                }
                else if(j == 6 && i >= 1 && i <= 6){
                    map[i][j] = new SimpleBooleanProperty(true);
                }
                else if(i == 6 && j <= 6 && j > 0){
                    map[i][j] = new SimpleBooleanProperty(true);
                }
                else if(i == 1 && j <= 6 && j > 0){
                    map[i][j] = new SimpleBooleanProperty(true);
                }
                else{
                    map[i][j] = new SimpleBooleanProperty(false);
                }
            }
        }
    }

    /*
    check the coordinate x,y is a path
    */
    public boolean CheckPath(int x, int y){
        if (map[x][y].get() == true){
            return true;
        }
        return false;
    }

    /*
    get the surrounding position of the specific cell 
    */
    public List<Pair<Integer,Integer>> getRange(Pair<Integer,Integer>pos, int radius) {
        List<Pair<Integer,Integer>> range = new ArrayList<>();
        for (int row=pos.getValue0()-radius; row<=pos.getValue0()+radius; ++row) {
            for (int column=pos.getValue0()-radius; column<=pos.getValue1()+radius; ++column) {
                if (row<=7 && row>=0 && column<=7 && column>=0) {
                    Pair<Integer,Integer> newCell = new Pair<Integer,Integer>(row, column);
                    range.add(newCell);
                }
            }
        }
        return range;
    }



    




}