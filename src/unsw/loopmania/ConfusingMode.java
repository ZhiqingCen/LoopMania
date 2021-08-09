package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class ConfusingMode {
    private boolean confusingRing = false;
    private boolean confusingAnduril = false;
    private boolean confusingTreeStump = false;
    private Random random;
    private String equipment;
    private TheOneRing theOneRing;
    private TreeStump treeStump;
    private Anduril anduril;

    public ConfusingMode(String equipment) {
        this.equipment = equipment;
        SimpleIntegerProperty x = new SimpleIntegerProperty(-1);
        SimpleIntegerProperty y = new SimpleIntegerProperty(-1);
        theOneRing = new TheOneRing(x, y);
        treeStump = new TreeStump(x, y);
        anduril = new Anduril(x, y);
        random = new Random();
        updateConfusingMode(random.nextInt(100));
    }

    public boolean getConfusingRing() {
        return confusingRing;
    }

    public boolean getConfusingAnduril() {
        return confusingAnduril;
    }

    public boolean getConfusingTreeStump() {
        return confusingTreeStump;
    }

    /**
     * add extra normal feature for rare item
     * @param character
     */
    public void addFeature(Character character) {
        if (confusingRing) {
            theOneRing.makeLive(character);
            System.err.println("-----Confusing mode: added TheOneRing features-----");
        } else if (confusingTreeStump) {
            treeStump.addFeature(character);
            System.err.println("-----Confusing mode: added TreeStump features-----");
        } else if (confusingAnduril) {
            anduril.addFeature(character);
            System.err.println("-----Confusing mode: added Anduril features-----");
        }
    }

    /**
     * remove extra normal feature for rare item
     * @param character
     */
    public void removeFeature(Character character) {
        if (confusingRing) {
            theOneRing.unequipped(character);
            System.err.println("-----Confusing mode: removed TheOneRing features-----");
        } else if (confusingTreeStump) {
            treeStump.unequipped(character);
            System.err.println("-----Confusing mode: removed TreeStump features-----");
        } else if (confusingAnduril) {
            anduril.unequipped(character);
            System.err.println("-----Confusing mode: removed Anduril features-----");
        }
    }

    /**
     * add extra boss feature for rare item
     * @param character
     */
    public void addBossFeature(Character character) {
        if (confusingTreeStump) {
            treeStump.addFeatureBoss(character);
            System.err.println("-----Confusing mode: added TreeStump boss features-----");
        } else if (confusingAnduril) {
            anduril.addFeatureBoss(character);
            System.err.println("-----Confusing mode: added Anduril boss features-----");
        }
    }

    /**
     * remove extra boss feature for rare item
     * @param character
     */
    public void removeBossFeature(Character character) {
        if (confusingTreeStump) {
            treeStump.unequippedBoss(character);
            System.err.println("-----Confusing mode: removed TreeStump boss features-----");
        } else if (confusingAnduril) {
            anduril.unequippedBoss(character);
            System.err.println("-----Confusing mode: removed Anduril boss features-----");
        }
    }

    /**
     * get random extra features from other rare items
     * @param randomInt
     */
    public void updateConfusingMode(int randomInt) {
        confusingRing = false;
        confusingAnduril = false;
        confusingTreeStump = false;
        switch (equipment) {
            case "Anduril":
                if (randomInt % 2 == 0) {
                    confusingRing = true;
                } else {
                    confusingTreeStump = true;
                }
                break;
            case "TheOneRing":
                if (randomInt % 2 == 0) {
                    confusingTreeStump = true;
                } else {
                    confusingAnduril = true;
                }
                break;
            case "TreeStump":
                if (randomInt % 2 == 0) {
                    confusingAnduril = true;
                } else {
                    confusingRing = true;
                }
                break;
            default:
                break;
        }
    }
}
