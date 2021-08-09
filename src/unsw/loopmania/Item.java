package unsw.loopmania;

public class Item {
    private int gold;
    private int experience;
    private int cycles;
    private int doggieCoin;
    private boolean doubled;
    private int priceOfDoggieCoin;

    public Item() {
        gold = 0;
        experience = 0;
        cycles = 0;
        doggieCoin = 0;
        priceOfDoggieCoin = 0;
        doubled = false;
    }

    public Item(int gold, int experience, int cycles) {
        this.setGold(gold);
        this.setExperience(experience);
        this.setCycles(cycles);
    }

    public int getPriceOfDoggieCoin() {
        return priceOfDoggieCoin;
    }

    public void setPriceOfDoggieCoin(int priceOfDoggieCoin) {
        this.priceOfDoggieCoin = priceOfDoggieCoin;
    }

    public void setDoubled(boolean ifDoubled) {
        doubled = ifDoubled;
    }

    public boolean getDoubled() {
        return doubled;
    }

    public int getDoggieCoin() {
        return doggieCoin;
    }

    public void setDoggieCoin(int doggieCoin) {
        this.doggieCoin = doggieCoin;
    }

    public int getCycles() {
        return cycles;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
