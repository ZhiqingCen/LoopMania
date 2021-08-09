package unsw.loopmania;

public class BuildingPathCheck {
    
    public BuildingPathCheck(){

    }

    public boolean VampireCastle(int x,int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return false;
        }else if((x==1 || x==6 ) && (y>=1 && y<=6)){
            return false;
        }else if((x==3 && y ==3) || (x==3 && y ==4)||(x==4 && y ==3)||(x==4 && y ==4)){
            return false;
        }else{
            return true;
        }
    }        
    public boolean ZombiePit(int x,int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return false;
        }else if((x==1 || x==6 ) && (y>=1 && y<=6)){
            return false;
        }else if((x==3 && y ==3) || (x==3 && y ==4)||(x==4 && y ==3)||(x==4 && y ==4)){
            return false;
        }else{
            return true;
        }
    }
    public boolean Tower(int x,int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return false;
        }else if((x==1 || x==6 ) && (y>=1 && y<=6)){
            return false;
        }else if((x==3 && y ==3) || (x==3 && y ==4)||(x==4 && y ==3)||(x==4 && y ==4)){
            return false;
        }else{
            return true;
        }
    }

    public boolean Village(int x,int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return true;
        }else if((x ==1 || x ==6 ) && (y>=1 && y<=6)){
            return true;
        }
        return false;        
    }

    public boolean Barracks(int x,int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return true;
        }else if((x ==1 || x ==6 ) && (y>=1 && y<=6)){
            return true;
        }
        return false;        
    }
    public boolean Trap(int x,int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return true;
        }else if((x ==1 || x ==6 ) && (y>=1 && y<=6)){
            return true;
        }
        return false;        
    }

    public boolean Campfire(int x, int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return false;
        }else if((x ==1 || x ==6 ) && (y>=1 && y<=6)){
            return false;
        }
        return true;
        
    }

    public boolean AF(int x, int y){
        if((x>=1 && x<=6) && (y == 1 || y==6)){
            return false;
        }else if((x ==1 || x ==6 ) && (y>=1 && y<=6)){
            return false;
        }
        return true;
        
    }
}